import inherite.User;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        char[] charArray = s.toCharArray();
        Set<Character> set = new HashSet<>(0);
        for (char c : charArray) {
            if ((int) c <= 127)
                set.add(c);
        }
        System.out.println(set.size());

    }

    public static int summery(int n){
        int summery = 0;
        while (n > 6){
            summery += n/3;
            n = n/3;
        }
        return summery;
    }

    @Test
    public void testSum(){
        int summery = summery(4);
        System.out.println(summery);
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
    public  void test1111(){
        int m = 3,n = 6,k = 0;
        while(m<n){
            ++k;
            m++;
            --n;
        }
        System.out.println(k);
    }

    public int resultan(int a, int n) {
        int b = 0;
        b = a;//缓存尾数
        int sum = 0;//累加器
        for (int i = 0; i < n; i++)//得到最大的那个数
        {
            sum += a;//累加求和
            a = a * 10;//前移一位
            a += b;//加尾数
            //
        }
        System.out.println("sum=" + sum);
        return sum;
    }

    @Test
    public void testan(){
      resultan(-2,5);
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }

    @Test
    public void testTwoSum(){
        int[] a1 = {1,3,5,6,9};
        int[] ints = twoSum(a1, 12);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }

//    @Test
//    public void testCollectionUtils() throws InvocationTargetException, IllegalAccessException {
//        User user1 = new User("a",1);
//        User user2 = new User("b",1);
//        User user3 = new User("c",1);
//        LinkedList<User> linkedList = new LinkedList<>();
//        linkedList.add(user1);
//        linkedList.add(user2);
//        linkedList.add(user3);
//        System.out.println(linkedList);
//        List<Object> arrayList = new ArrayList<>();
//        CollectionUtils.addAll(arrayList,linkedList);
//        System.out.println("arrayList: "+ arrayList);
//        user3.setAge(2);
//
//        System.out.println("user2: "+user2);
//        System.out.println(linkedList);
//        System.out.println("arrayList: "+ arrayList);
//        arrayList = arrayList.stream().map(this::transformObject).collect(Collectors.toList());
//        System.out.println(arrayList);
//
//    }

    public User transformObject(User o){
        User cloneBean;
        try {
            cloneBean = (User) BeanUtils.cloneBean(o);
            System.out.println(cloneBean);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return cloneBean;
    }

    @Test
    public void test21() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        User user = new User("a",1);
        User user1 = new User("b",2);

//        Object o = BeanUtils.cloneBean(user);
        User o  = new User("c",2);
//        o = BeanUtils.copyProperties(user1);
        System.out.println(o);
        System.out.println(user);
        System.out.println(user1);
    }





}
