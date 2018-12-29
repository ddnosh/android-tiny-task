# android-tiny-task
a tiny asynchronized task library for android.

# Solution
1. we should avoid to use thread, runnable, handler, and AsyncTask;
2. we should use thread pool executors to handle threads;
3. we will handle background thread and UI thread, and their interactions, even frequently;
4. simple and easy to use, focus lifecycle and avoid OOM.

# Function
1. handle asynchronized task in background;
2. handle asynchronized task first in background and then to front;
3. handle asynchronized task with delay;
4. cancel handle asynchronized task.

# Technology
1. Desing Pattern
    1. single pattern
    2. static
2. Skill Point
    1. ExecutorService
    2. synchronized
    3. Callable
    4. FutureTask
    5. Handler
    6. Runnable

# Usage
1. only run in background
TinyTaskExecutor.execute(new SimpleTask<T>() { ... });
2. run in background and then go back to main ui;
TinyTaskExecutor.execute(new Task<String>() { ... });
3. run with delay
TinyTaskExecutor.execute(task, 5000);
4. remove delay task
TinyTaskExecutor.removeTask(task);
5. check a task(not recommend)
TinyTaskExecutor.check();

# TODO
1. to cancel when overtime has come;
2. schedule task;