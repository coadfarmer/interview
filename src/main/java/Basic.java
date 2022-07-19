import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class Basic {


//    public static void main(String[] args) {
//        List<Integer> integerList = new ArrayList<>();
//        integerList.add(2);
//        integerList.add(5);
//        integerList.add(7);
//        integerList.add(1);
//        System.out.println(integerList);
//        integerList.sort((o1, o2) -> o2 - o1);
//        System.out.println(integerList);
//    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println("线程1运行中")).start();

            new Thread(() -> {
                System.out.println("线程2运行中");
            }).start();
        }

    }

    @Test
    public void test() {
        List<String> list = randomString();
        Random random = new Random();
        System.out.println(random.nextInt(32));
        if (random.nextInt(32) < 0) {
            System.out.println(
            );
        }
    }

    public List<String> randomString() {
        //将所有的大小写字母和0-9数字存入字符串中
        String str = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        Random random = new Random();
        List<String> listString = new ArrayList<String>();
        //生成10条长度为1-10的随机字符串
        for (int i = 0; i < 10; i++) {
            StringBuilder stringBuffer = new StringBuilder();
            //确定字符串长度
            int stringLength = random.nextInt(10);
            for (int j = 0; j < stringLength; j++) {
                //先随机生成初始定义的字符串 str 的某个索引，以获取相应的字符
                int index = random.nextInt(str.length());
                char c = str.charAt(index);
                stringBuffer.append(c);
            }
            //判断当前的list容器中是否已有刚生成的字符串，满足每条字符串不可重复性
            if (!(listString.contains(stringBuffer.toString()))) {
                listString.add(stringBuffer.toString());
            } else {
                i--;
            }

        }
        return listString;
    }

    @Test
    public void testArrayList() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        long l1 = System.currentTimeMillis();
        arrayList.ensureCapacity(134217728);
        for (int i = 0; i < Integer.MAX_VALUE >> 4; i++) {
            arrayList.add((int) (Math.random() * 10000));
        }
        System.out.println(System.currentTimeMillis() - l1);
        System.out.println(arrayList.size());
    }

    @Test
    public void testHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "22sdfawerffdfgdsgert534534342");
        hashMap.forEach((s, s2) -> System.out.println(hashMap.get(s).hashCode()));
    }

    @Test
    public void testLinkedBlockingQueue() {
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(2);
//        for (int i = 0; i < 3; i++) {
        try {
            linkedBlockingQueue.put(1);
            linkedBlockingQueue.put(2);
            linkedBlockingQueue.put(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        }
        linkedBlockingQueue.forEach(System.out::println);
    }

    @Test
    public void testArrayBlockQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(2);
        try {
            blockingQueue.put(1);
            blockingQueue.put(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        blockingQueue.forEach(System.out::println);
        System.out.println(blockingQueue.peek());
        blockingQueue.take();
        System.out.println(blockingQueue.peek());

    }

    @Test
    public void testStringLength() {
        String s = "a";
        System.out.println(s.length());
        System.out.println("我".length());
    }

    @Test
    public void testBitMove() {
        int i = 8;
        System.out.println((i >> 2) + 1);
    }

    @Test
    public void testStringsToIntegerList(){
        String[] strings = {"23","65","20"};
        List<Integer> integerList = Arrays.stream(strings).map(Integer::valueOf).collect(Collectors.toList());
        System.out.println(integerList);
    }


}
