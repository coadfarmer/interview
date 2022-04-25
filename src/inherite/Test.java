package inherite;

public class Test {

    @org.junit.Test
    public void test() {
        Son son = new Son();
        Dad dad = new Dad();
        System.out.println(dad.getString());
        System.out.println();
    }

}
