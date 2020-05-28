public class HelloJava {
    public static void main(String[] args) {
        // Parametros
        System.out.println("------------------------------------------------");
        for (int i = 0; i < args.length; i++) {
            System.out.println("Hello " + args[i]);
            System.out.println("Quantidade de Caracteres: " + args[i].length());
        }
        System.out.println("------------------------------------------------");
    }
}
