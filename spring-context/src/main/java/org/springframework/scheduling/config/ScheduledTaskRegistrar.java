/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.scheduling.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Helper bean for registering tasks with a {@link TaskScheduler}, typically using cron
 * expressions.
 *
 * <p>As of Spring 3.1, {@code ScheduledTaskRegistrar} has a more prominent user-facing
 * role when used in conjunction with the @{@link
 * org.springframework.scheduling.annotation.EnableAsync EnableAsync} annotation and its
 * {@link org.springframework.scheduling.annotation.SchedulingConfigurer
 * SchedulingConfigurer} callback interface.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @author Tobias Montagna-Hay
 * @since 3.0
 * @see org.springframework.scheduling.annotation.EnableAsync
 * @see org.springframework.scheduling.annotation.SchedulingConfigurer
 */
public class ScheduledTaskRegistrar implements InitializingBean, DisposableBean {

    private TaskScheduler taskScheduler;

    private ScheduledExecutorService localExecutor;

    private List<TriggerTask> triggerTasks;

    private List<CronTask> cronTasks;

    private List<IntervalTask> fixedRateTasks;

    private List<IntervalTask> fixedDelayTasks;

    private final Set<ScheduledFuture<?>> scheduledFutures = new LinkedHashSet<ScheduledFuture<?>>();


    /**
     * Set the {@link TaskScheduler} to register scheduled tasks with.
     */
    public void setTaskScheduler(TaskScheduler taskScheduler) {
        Assert.notNull(taskScheduler, "TaskScheduler must not be null");
        this.taskScheduler = taskScheduler;
    }

    /**
     * Set the {@link TaskScheduler} to register scheduled tasks with, or a
     * {@link java.util.concurrent.ScheduledExecutorService} to be wrapped as a
     * {@code TaskScheduler}.
     */
    public void setScheduler(Object scheduler) {
        Assert.notNull(scheduler, "Scheduler object must not be null");
        if (scheduler instanceof TaskScheduler) {
            this.taskScheduler = (TaskScheduler) scheduler;
        } else if (scheduler instanceof ScheduledExecutorService) {
            this.taskScheduler = new ConcurrentTaskScheduler(((ScheduledExecutorService) scheduler));
        } else {
            throw new IllegalArgumentException("Unsupported scheduler type: " + scheduler.getClass());
        }
    }

    /**
     * Return the {@link TaskScheduler} instance for this registrar (may be {@code null}).
     */
    public TaskScheduler getScheduler() {
        return this.taskScheduler;
    }


    /**
     * Specify triggered tasks as a Map of Runnables (the tasks) and Trigger objects
     * (typically custom implementations of the {@link Trigger} interface).
     */
    public void setTriggerTasks(Map<Runnable, Trigger> triggerTasks) {
        this.triggerTasks = new ArrayList<TriggerTask>();
        for (Map.Entry<Runnable, Trigger> task : triggerTasks.entrySet()) {
            addTriggerTask(new TriggerTask(task.getKey(), task.getValue()));
        }
    }

    /**
     * Specify triggered tasks as a list of {@link TriggerTask} objects. Primarily used
     * by {@code <task:*>} namespace parsing.
     * @since 3.2
     * @see ScheduledTasksBeanDefinitionParser
     */
    public void setTriggerTasksList(List<TriggerTask> triggerTasks) {
        this.triggerTasks = triggerTasks;
    }

    /**
     * Get the trigger tasks as an unmodifiable list of {@link TriggerTask} objects.
     * @return the list of tasks (never {@code null})
     * @since 4.2
     */
    public List<TriggerTask> getTriggerTaskList() {
        return (this.triggerTasks != null ? Collections.unmodifiableList(this.triggerTasks)
                : Collections.<TriggerTask>emptyList());
    }

    /**
     * Specify triggered tasks as a Map of Runnables (the tasks) and cron expressions.
     * @see CronTrigger
     */
    public void setCronTasks(Map<Runnable, String> cronTasks) {
        this.cronTasks = new ArrayList<CronTask>();
        for (Map.Entry<Runnable, String> task : cronTasks.entrySet()) {
            addCronTask(task.getKey(), task.getValue());
        }
    }

    /**
     * Specify triggered tasks as a list of {@link CronTask} objects. Primarily used by
     * {@code <task:*>} namespace parsing.
     * @since 3.2
     * @see ScheduledTasksBeanDefinitionParser
     */
    public void setCronTasksList(List<CronTask> cronTasks) {
        this.cronTasks = cronTasks;
    }

    /**
     * Get the cron tasks as an unmodifiable list of {@link CronTask} objects.
     * @return the list of tasks (never {@code null})
     * @since 4.2
     */
    public List<CronTask> getCronTaskList() {
        return (this.cronTasks != null ? Collections.unmodifiableList(this.cronTasks)
                : Collections.<CronTask>emptyList());
    }

    /**
     * Specify triggered tasks as a Map of Runnables (the tasks) and fixed-rate values.
     * @see TaskScheduler#scheduleAtFixedRate(Runnable, long)
     */
    public void setFixedRateTasks(Map<Runnable, Long> fixedRateTasks) {
        this.fixedRateTasks = new ArrayList<IntervalTask>();
        for (Map.Entry<Runnable, Long> task : fixedRateTasks.entrySet()) {
            addFixedRateTask(task.getKey(), task.getValue());
        }
    }

    /**
     * Specify fixed-rate tasks as a list of {@link IntervalTask} objects. Primarily used
     * by {@code <task:*>} namespace parsing.
     * @since 3.2
     * @see ScheduledTasksBeanDefinitionParser
     */
    public void setFixedRateTasksList(List<IntervalTask> fixedRateTasks) {
        this.fixedRateTasks = fixedRateTasks;
    }

    /**
     * Get the fixed-rate tasks as an unmodifiable list of {@link IntervalTask} objects.
     * @return the list of tasks (never {@code null})
     * @since 4.2
     */
    public List<IntervalTask> getFixedRateTaskList() {
        return (this.fixedRateTasks != null ? Collections.unmodifiableList(this.fixedRateTasks)
                : Collections.<IntervalTask>emptyList());
    }

    /**
     * Specify triggered tasks as a Map of Runnables (the tasks) and fixed-delay values.
     * @see TaskScheduler#scheduleWithFixedDelay(Runnable, long)
     */
    public void setFixedDelayTasks(Map<Runnable, Long> fixedDelayTasks) {
        this.fixedDelayTasks = new ArrayList<IntervalTask>();
        for (Map.Entry<Runnable, Long> task : fixedDelayTasks.entrySet()) {
            addFixedDelayTask(task.getKey(), task.getValue());
        }
    }

    /**
     * Specify fixed-delay tasks as a list of {@link IntervalTask} objects. Primarily used
     * by {@code <task:*>} namespace parsing.
     * @since 3.2
     * @see ScheduledTasksBeanDefinitionParser
     */
    public void setFixedDelayTasksList(List<IntervalTask> fixedDelayTasks) {
        this.fixedDelayTasks = fixedDelayTasks;
    }

    /**
     * Get the fixed-delay tasks as an unmodifiable list of {@link IntervalTask} objects.
     * @return the list of tasks (never {@code null})
     * @since 4.2
     */
    public List<IntervalTask> getFixedDelayTaskList() {
        return (this.fixedDelayTasks != null ? Collections.unmodifiableList(this.fixedDelayTasks)
                : Collections.<IntervalTask>emptyList());
    }

    /**
     * Add a Runnable task to be triggered per the given {@link Trigger}.
     * @see TaskScheduler#scheduleAtFixedRate(Runnable, long)
     */
    public void addTriggerTask(Runnable task, Trigger trigger) {
        addTriggerTask(new TriggerTask(task, trigger));
    }

    /**
     * Add a {@code TriggerTask}.
     * @since 3.2
     * @see TaskScheduler#scheduleAtFixedRate(Runnable, long)
     */
    public void addTriggerTask(TriggerTask task) {
        if (this.triggerTasks == null) {
            this.triggerTasks = new ArrayList<TriggerTask>();
        }
        this.triggerTasks.add(task);
    }

    /**
     * Add a Runnable task to be triggered per the given cron expression
     */
    public void addCronTask(Runnable task, String expression) {
        addCronTask(new CronTask(task, expression));
    }

    /**
     * Add a {@link CronTask}.
     * @since 3.2
     */
    public void addCronTask(CronTask task) {
        if (this.cronTasks == null) {
            this.cronTasks = new ArrayList<CronTask>();
        }
        this.cronTasks.add(task);
    }

    /**
     * Add a {@code Runnable} task to be triggered at the given fixed-rate interval.
     * @see TaskScheduler#scheduleAtFixedRate(Runnable, long)
     */
    public void addFixedRateTask(Runnable task, long interval) {
        addFixedRateTask(new IntervalTask(task, interval, 0));
    }

    /**
     * Add a fixed-rate {@link IntervalTask}.
     * @since 3.2
     * @see TaskScheduler#scheduleAtFixedRate(Runnable, long)
     */
    public void addFixedRateTask(IntervalTask task) {
        if (this.fixedRateTasks == null) {
            this.fixedRateTasks = new ArrayList<IntervalTask>();
        }
        this.fixedRateTasks.add(task);
    }

    /**
     * Add a Runnable task to be triggered with the given fixed delay.
     * @see TaskScheduler#scheduleWithFixedDelay(Runnable, long)
     */
    public void addFixedDelayTask(Runnable task, long delay) {
        addFixedDelayTask(new IntervalTask(task, delay, 0));
    }

    /**
     * Add a fixed-delay {@link IntervalTask}.
     * @since 3.2
     * @see TaskScheduler#scheduleWithFixedDelay(Runnable, long)
     */
    public void addFixedDelayTask(IntervalTask task) {
        if (this.fixedDelayTasks == null) {
            this.fixedDelayTasks = new ArrayList<IntervalTask>();
        }
        this.fixedDelayTasks.add(task);
    }

    /**
     * Return whether this {@code ScheduledTaskRegistrar} has any tasks registered.
     * @since 3.2
     */
    public boolean hasTasks() {
        return (!CollectionUtils.isEmpty(this.triggerTasks) || !CollectionUtils.isEmpty(this.cronTasks)
                || !CollectionUtils.isEmpty(this.fixedRateTasks) || !CollectionUtils.isEmpty(this.fixedDelayTasks));
    }


    /**
     * Calls {@link #scheduleTasks()} at bean construction time.
     */
    @Override
    public void afterPropertiesSet() {
        scheduleTasks();
    }

    /**
     * Schedule all registered tasks against the underlying {@linkplain
     * #setTaskScheduler(TaskScheduler) task scheduler}.
     */
    protected void scheduleTasks() {
        long now = System.currentTimeMillis();

        if (this.taskScheduler == null) {
            this.localExecutor = Executors.newSingleThreadScheduledExecutor();
            this.taskScheduler = new ConcurrentTaskScheduler(this.localExecutor);
        }
        if (this.triggerTasks != null) {
            for (TriggerTask task : this.triggerTasks) {
                this.scheduledFutures.add(this.taskScheduler.schedule(task.getRunnable(), task.getTrigger()));
            }
        }
        if (this.cronTasks != null) {
            for (CronTask task : this.cronTasks) {
                this.scheduledFutures.add(this.taskScheduler.schedule(task.getRunnable(), task.getTrigger()));
            }
        }
        if (this.fixedRateTasks != null) {
            for (IntervalTask task : this.fixedRateTasks) {
                if (task.getInitialDelay() > 0) {
                    Date startTime = new Date(now + task.getInitialDelay());
                    this.scheduledFutures.add(
                            this.taskScheduler.scheduleAtFixedRate(task.getRunnable(), startTime, task.getInterval()));
                } else {
                    this.scheduledFutures
                            .add(this.taskScheduler.scheduleAtFixedRate(task.getRunnable(), task.getInterval()));
                }
            }
        }
        if (this.fixedDelayTasks != null) {
            for (IntervalTask task : this.fixedDelayTasks) {
                if (task.getInitialDelay() > 0) {
                    Date startTime = new Date(now + task.getInitialDelay());
                    this.scheduledFutures.add(this.taskScheduler.scheduleWithFixedDelay(task.getRunnable(), startTime,
                            task.getInterval()));
                } else {
                    this.scheduledFutures
                            .add(this.taskScheduler.scheduleWithFixedDelay(task.getRunnable(), task.getInterval()));
                }
            }
        }
    }

    @Override
    public void destroy() {
        for (ScheduledFuture<?> future : this.scheduledFutures) {
            future.cancel(true);
        }
        if (this.localExecutor != null) {
            this.localExecutor.shutdownNow();
        }
    }

}
