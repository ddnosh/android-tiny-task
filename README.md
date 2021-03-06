# android-tiny-task
[![Download](https://api.bintray.com/packages/ddnosh/maven/tinytask/images/download.svg) ](https://bintray.com/ddnosh/maven/tinytask/_latestVersion)  
A tiny asynchronized task library for android.  

![在这里插入图片描述](https://github.com/ddnosh/githubusercontent/blob/master/image/android-tiny-task-readme.jpg?raw=true)

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
5. set task priority.

# Technology
1. Desing Pattern
    1. Singleton Pattern
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
TinyTaskExecutor.execute(new AdvancedTask<String>() { ... });
3. run with delay
TinyTaskExecutor.execute(task, 5000);
4. remove delay task
TinyTaskExecutor.removeTask(task);
5. check a task(not recommend)
TinyTaskExecutor.check();
6. post to main thread
TinyTaskExecutor.postToMainThread(runnable, 2000);
7. remove post to main thread
TinyTaskExecutor.removeMainThreadRunnable(delayRunnable);
8. set task priority
new TestTask(TinyTaskExecutor.PRIORITY_LOWEST);
TinyTaskExecutor.execute(s1);

# TODO
1. to cancel when overtime has come;
2. schedule task;
3. ~~(done in 20190105) task priority~~;
4. ~~(done in 20190119) BlockQueue~~;
5. ~~(done in 20190119) customized thread pool and queue~~;
