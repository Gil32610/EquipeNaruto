package MyCompiler;
public class App {
    public static void main(String[] args) throws Exception {
       System.out.println(isLetra('_'));
    }
    public static boolean isLetra(char c) {
        return (c >= 'a') && (c <= 'z');
    }
}
