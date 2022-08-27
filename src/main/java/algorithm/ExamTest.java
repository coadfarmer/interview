package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExamTest {

    public static String objectToJson(List<User> userList) {
        // 请在这里输入代码
        StringBuilder stringBuilder = new StringBuilder().append("{");
        Map<String, List<User>> stringListMap = userList.stream().collect(Collectors.groupingBy(User::getSex));
        for (String s : stringListMap.keySet()) {
            stringBuilder.append(s).append(":");
            stringBuilder.append("[");
            for (int i = 0; i < stringListMap.get(s).size(); i++) {
                stringBuilder.append(stringListMap.get(s).get(i).toString());
                if(i < stringListMap.get(s).size()-1){
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("],");
        }
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        substring = substring+"}";
        return substring;
    }

    static class User {
        private String name;
        private String sex;

        public User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return " { " + " name: '" + name + '\'' + ", sex:'" + sex + '\'' + '}';
        }
    }

    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User("nihao","男"));
        userList.add(new User("nihao1","男"));
        userList.add(new User("nihao2","女"));
        userList.add(new User("nihao3","女"));
        userList.add(new User("nihao4","男"));

        String s = objectToJson(userList);
        System.out.println(s);
    }
}
