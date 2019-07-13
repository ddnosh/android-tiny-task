package com.androidwind.task.sample;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidwind.task.Task;
import com.androidwind.task.TinyTaskExecutor;
import com.androidwind.task.sample.onebyone.BaseSyncTask;
import com.androidwind.task.sample.onebyone.TinySyncExecutor;

import java.util.ArrayDeque;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SynchronizedFragment extends Fragment implements View.OnClickListener {

    private ValueAnimator valueAnimator1, valueAnimator2, valueAnimator3, valueAnimator4;

    public static SynchronizedFragment newInstance() {
        SynchronizedFragment fragment = new SynchronizedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_synchronized, container, false);
        Button btn1 = view.findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        Button btn2 = view.findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        Button btn3 = view.findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        Button btn4 = view.findViewById(R.id.btn_add);
        btn4.setOnClickListener(this);
        initView(view);
        return view;
    }

    private void initView(View view) {
        final Button btnAnim1 = view.findViewById(R.id.btn_anim_1);
        valueAnimator1 = ValueAnimator.ofInt(btnAnim1.getLayoutParams().width, 500);
        valueAnimator1.setDuration(1000);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                btnAnim1.getLayoutParams().width = currentValue;
                btnAnim1.requestLayout();
            }
        });
        valueAnimator1.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                System.out.println("[OneByOne]anim played 1 secs");
                TinySyncExecutor.getInstance().finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        final Button btnAnim2 = view.findViewById(R.id.btn_anim_2);
        valueAnimator2 = ValueAnimator.ofInt(btnAnim2.getLayoutParams().width, 800);
        valueAnimator2.setDuration(2000);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                btnAnim2.getLayoutParams().width = currentValue;
                btnAnim2.requestLayout();
            }
        });
        valueAnimator2.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                System.out.println("[OneByOne]anim played 2 secs");
                TinySyncExecutor.getInstance().finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        final Button btnAnim3 = view.findViewById(R.id.btn_anim_3);
        valueAnimator3 = ValueAnimator.ofInt(btnAnim3.getLayoutParams().height, 300);
        valueAnimator3.setDuration(3000);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentValue = (Integer) animator.getAnimatedValue();
                btnAnim3.getLayoutParams().height = currentValue;
                btnAnim3.requestLayout();
            }
        });
        valueAnimator3.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                System.out.println("[OneByOne]anim played 3 secs");
                TinySyncExecutor.getInstance().finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        final Button btnAnim4 = view.findViewById(R.id.btn_anim_4);
        valueAnimator4 = new ValueAnimator();
        valueAnimator4.setFloatValues(1.0f,0.1f);
        final float alpha = btnAnim4.getAlpha();
        valueAnimator4.setDuration(3000);
        valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                float rate = (float) animator.getAnimatedValue();
                btnAnim4.setAlpha(rate*alpha);
            }
        });
        valueAnimator4.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                System.out.println("[OneByOne]anim played 4 secs");
                TinySyncExecutor.getInstance().finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                test1();
                break;
            case R.id.btn_2:
                test2();
                break;
            case R.id.btn_3:
                test3();
                break;
            case R.id.btn_add:
                final BaseSyncTask task = new BaseSyncTask() {
                    @Override
                    public void doTask() {
                        valueAnimator4.start();
                    }
                };
                TinySyncExecutor.getInstance().enqueue(task);
                break;
        }
    }

    //1 --------------------------------------------------------------------------------------------
//    private void test1() {
//        for (int i = 0; i < 10; i++) {
//            execute(new SimpleTask<String>(i + "") {
//                @Override
//                public String doInBackground() {
//                    try {
//                        System.out.println("The taskName is :" + getTaskName());
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return "simple task with sleep 1 sec";
//                }
//            });
//        }
//    }
//
//    private TaskRunnable mActive;
//    private ArrayDeque<TaskRunnable> mArrayDeque = new ArrayDeque<>();
//
//    public synchronized void execute(final SimpleTask task) {
//        mArrayDeque.offer(new TaskRunnable() {
//            @Override
//            public Object call() throws Exception {
//                try {
//                    task.call();
//                } finally {
//                    scheduleNext();
//                }
//                return null;
//            }
//        });

//        if (mActive == null) {
//            scheduleNext();
//        }
//    }
//
//    private void scheduleNext() {
//        if ((mActive = mArrayDeque.poll()) != null) {
//            TinyTaskExecutor.execute(mActive);
//        }
//    }

    //2 --------------------------------------------------------------------------------------------
//    private void test1() {
//        for (int i = 0; i < 10; i++) {
//            final int j = i;
//            execute1(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("The taskName is :" + (j + 1));
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//    }
//
//    private TaskRunnable mActive;
//    private ArrayDeque<TaskRunnable> mArrayDeque = new ArrayDeque<>();
//
//    public synchronized void execute1(final Runnable r) {
//        mArrayDeque.offer(new TaskRunnable() {
//            @Override
//            public Object call() throws Exception {
//                try {
//                    r.run();
//                } finally {
//                    scheduleNext();
//                }
//                return null;
//            }
//        });

//        if (mActive == null) {
//            scheduleNext();
//        }
//    }
//
//    private void scheduleNext() {
//        if ((mActive = mArrayDeque.poll()) != null) {
//            TinyTaskExecutor.execute(mActive);
//        }
//    }

    //3 --------------------------------------------------------------------------------------------
    private void test1() {
        for (int i = 0; i < 10; i++) {
            final int j = i;
            execute1(new TaskCallBack() {
                @Override
                public void onBackground() {
                    System.out.println("[onBackground]The taskName is :" + (j + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess() {
                    System.out.println("[onSuccess]The taskName is :" + (j + 1));
                }

                @Override
                public void onFail() {
                    System.out.println("[onFail]The taskName is :" + (j + 1));
                    Toast.makeText(getContext(), "The fail taskName is :" + (j + 1), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Task mActive;
    private ArrayDeque<Task> mArrayDeque = new ArrayDeque<>();

    public synchronized void execute1(final TaskCallBack callBack) {
        mArrayDeque.offer(new Task() {
            @Override
            public Object doInBackground() {
                callBack.onBackground();
                return null;
            }

            @Override
            public void onSuccess(Object o) {
                try {
                    callBack.onSuccess();
                } finally {
                    scheduleNext();
                }
            }

            @Override
            public void onFail(Throwable throwable) {
                try {
                    callBack.onFail();
                } finally {
                    scheduleNext();
                }
            }
        });

        if (mActive == null) {
            scheduleNext();
        }
    }

    private void scheduleNext() {
        if ((mActive = mArrayDeque.poll()) != null) {
            TinyTaskExecutor.execute(mActive);
        }
    }

    public interface TaskCallBack {
        void onBackground();

        void onSuccess();

        void onFail();
    }

    //4 --------------------------------------------------------------------------------------------
    final ArrayBlockingQueue<Runnable> valve = new ArrayBlockingQueue<>(1);

    private void test2() {
        //put
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    final int j = i;
                    execute2(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("The taskName is :" + (j + 1));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }).start();
        //take
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Runnable s = null;
                    try {
                        s = valve.take();
                        s.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public synchronized void execute2(final Runnable r) {
        try {
            valve.put(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //5 --------------------------------------------------------------------------------------------
    private void test3() {
        final BaseSyncTask task1 = new BaseSyncTask() {
            @Override
            public void doTask() {
//                SimCallBack cb = new SimCallBack() {
//                    @Override
//                    public void onComplete() {
//                        try {
//                            Thread.sleep(1000);
//                            System.out.println("[OneByOne]sleep 1 secs");
//                            TinySyncExecutor.getInstance().finish();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                cb.onComplete();
                valueAnimator1.start();
            }
        };
        final BaseSyncTask task2 = new BaseSyncTask() {
            @Override
            public void doTask() {
//                SimCallBack cb = new SimCallBack() {
//                    @Override
//                    public void onComplete() {
//                        try {
//                            Thread.sleep(2000);
//                            System.out.println("[OneByOne]sleep 2 secs");
//                            TinySyncExecutor.getInstance().finish();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                cb.onComplete();
                valueAnimator2.start();
            }
        };
        final BaseSyncTask task3 = new BaseSyncTask() {
            @Override
            public void doTask() {
//                SimCallBack cb = new SimCallBack() {
//                    @Override
//                    public void onComplete() {
//                        try {
//                            Thread.sleep(3000);
//                            System.out.println("[OneByOne]sleep 3 secs");
//                            TinySyncExecutor.getInstance().finish();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                cb.onComplete();
                valueAnimator3.start();
            }
        };
        TinySyncExecutor.getInstance().enqueue(task1);
        TinySyncExecutor.getInstance().enqueue(task2);
        TinySyncExecutor.getInstance().enqueue(task3);
    }

    public interface SimCallBack {
        void onComplete();
    }
}
