import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Basic {


    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(5);
        integerList.add(7);
        integerList.add(1);
        System.out.println(integerList);
        integerList.sort((o1, o2) -> o2 - o1);
        System.out.println(integerList);
    }

    @Test
    public void test(){
        List<String> list = randomString();
        Random random = new Random();
        System.out.println(random.nextInt(32));
        if(random.nextInt(32)<0){
            System.out.println(
            );
        }
    }

    public List<String> randomString(){
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
            }else {
                i--;
            }

        }
        return listString;
    }


}
