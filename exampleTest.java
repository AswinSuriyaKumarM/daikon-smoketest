public class exampleTest {
    public static void main(String[] args) {
        example e = new example();

        e.inc();
        e.inc();
        e.add(3);
        e.add(5);

        System.out.println(e.getCount());
        System.out.println(e.isNonNegative());

        example e2 = new example();
        e2.add(10);
        e2.inc();

        System.out.println(e2.getCount());
        System.out.println(e2.isNonNegative());
    }
}