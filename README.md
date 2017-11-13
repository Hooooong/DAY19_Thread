Android Programing
----------------------------------------------------
### 2017.09.28 13일차

#### 예제
____________________________________________________

#### 공부정리
____________________________________________________

##### __Thread__

- Thread 란?

  > Thread는 어떠한 프로그램 내에서, 특히 Process 내에서 실행되는 흐름의 단위를 말한다. 일반적으로 한 프로그램은 하나의 스레드를 가지고 있지만, 프로그램 환경에 따라 둘 이상의 스레드를 동시에 실행할 수 있다. 이러한 실행 방식을 멀티스레드(multithread)라고 한다.

  - Process 는 현재 실행되고 있는 최소 하나의 Thread 로 구성된 프로그램을 말한다.

  - Android 에서는 어플리케이션이 실행이 되면 하나의 실행 Thread 로 어플리케이션의 Linux 프로세스를 시작한다.



- Multi Process vs Multi Thread

  > 멀티프로세스와 멀티스레드는 양쪽 모두 여러 흐름이 동시에 진행된다는 공통점을 가지고 있다. 하지만 멀티프로세스에서 각 프로세스는 독립적으로 실행되며 각각 별개의 메모리를 차지하고 있는 것과 달리 멀티스레드는 프로세스 내의 메모리를 공유해 사용할 수 있다. 또한 프로세스 간의 전환 속도보다 스레드 간의 전환 속도가 빠르다.

  - 사실상 Process 와 Thread 는 단위 자체가 다르다. ( Process > Thread )

- Thread 사용법

  - 기본적으로 Thread 는 `Thread` 를 상속(extends)받거나 `Runnable` 을 구현(implements)하는 방법으로 사용한다.

  1. Thread 상속

      ```java
      public class Example1{
      	public static void main(String[] args) {
      		Thread test = new ThreadTest();
      		// Thread 시작
      		test.start();
      	}
      }

      class ThreadTest extends Thread{
      	@Override
      	public void run() {
      		// Thread 실행 구간
      	}
      }
      ```

  2. Runnable 구현

      ```java
      public class Example2 implements Runnable{

      	public static void main(String[] args) {

      		Thread test = new Thread(new Example2());
      		// Thread 시작
      		test.start();
      	}

      	@Override
      	public void run() {
      		// Thread 실행 구간
      	}
      }
      ```

- Android 에서 Thread 사용

  - Android UI 는 기본적으로 Main Thread 를 주축으로 하는 Single Thread 모델로 동작한다. 그렇기 떄문에 Main Thread 에서는 긴 작업을 피해야 한다.

  - 특히 UI 를 변경하는 작업은 Main Thread 에서만 가능하기 때문에 Sub Thread 에서 UI 를 변경하기 위해서는 `Handler` 와 `runOnUiThread`, `AsyncTask` 를 사용한다.

  1. Handler 사용

      ![Handler](https://github.com/Hooooong/DAY19_Thread/blob/master/image/handler.PNG)

      ```java
      public class MainActivity extends AppCompatActivity {
          Rotate rotate;

          @Override
          protected void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
              setContentView(R.layout.activity_main);
              // Thread 에 Handler 객체를 넘겨준다.
              rotate = new Rotate(handler);
              // Thrad 시작
              rotate.start();
          }

          // Handler 객체 생성
          Handler handler = new Handler(){
              @Override
              public void handleMessage(Message msg) {
                  switch (msg.what){
                      case ACTION_SET:
                          float curRot = button.getRotation();
                          button.setRotation(curRot + 6);
                          break;
                  }
              }
          };
      }

      class Rotate extends Thread {

          Handler handler;

          public Rotate(Handler handler) {
              this.handler = handler;
          }

          @Override
          public void run() {
              while (true) {
                  // handler 에 메세지를 담아 보낸다.
                  sendMessageToHandler();
                  try {
                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }

          // Handler 에 메세지를 보내는 메소드
          public void sendMessageToHandler(){
              // Message 객체 생성
              Message message = new Message();
              // Message 객체에 넘겨줄 값들을 담는다.
              // what, obj, arg1, arg2 가 있다.
              message.what = MainActivity.ACTION_SET;
              handler.sendMessage(message);
          }
      }
      ```

  2. runOnUiThread 사용

      ```java
      new Thread(new Runnable() {
          @Override
          public void run() {
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      // UI 에 대한 작업 실행
                  }
              });
          }
      }).start();
      ```

  3. AsyncTask 사용

      - 참조 : [AsyncTask](https://github.com/Hooooong/DAY25_HTTPConnect#asynctask)

- 참조 : [Thread 란?](https://ko.wikipedia.org/wiki/%EC%8A%A4%EB%A0%88%EB%93%9C)
