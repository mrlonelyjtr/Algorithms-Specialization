## Programming Assignment

### Question

![Markdown](http://i2.muimg.com/1949/e933d24414043bdd.png)

### Answer

{% codeblock lang:java %}
public class Main {

    public static void main(String[] args) {
        String num1 = "3141592653589793238462643383279502884197169399375105820974944592";
        String num2 = "2718281828459045235360287471352662497757247093699959574966967627";

        // Answer: 8539734222673567065463550869546574495034888535765114961879601127067743044893204848617875072216249073013374895871952806582723184
        System.out.println(karatsubaMul(num1, num2));
    }

    private static String karatsubaMul(String num1, String num2){
        int n = num1.length();

        if (n == 1)
            return String.valueOf(Integer.parseInt(num1) * Integer.parseInt(num2));

        String a = num1.substring(0, n / 2);
        String b = num1.substring(n / 2);
        String c = num2.substring(0, n / 2);
        String d = num2.substring(n / 2);

        String ac = karatsubaMul(a, c);
        String ad = karatsubaMul(a, d);
        String bc = karatsubaMul(b, c);
        String bd = karatsubaMul(b, d);
        String result1 = ac + String.format("%0" + n + "d", 0);
        String result2 = add(ad, bc) + String.format("%0" + n / 2 + "d", 0);

        return add(add(result1, result2), bd);
    }

    private static String add(String num1, String num2){
        if (num1.length() > num2.length())
            num2 = String.format("%" + num1.length() + "s", num2).replace(' ', '0');
        else if (num1.length() < num2.length())
            num1 = String.format("%" + num2.length() + "s", num1).replace(' ', '0');

        int carry = 0;
        String result = "";

        for (int i = num1.length() - 1; i >= 0; i--){
            int a = num1.charAt(i) - '0';
            int b = num2.charAt(i) - '0';

            int sum = carry + a + b;
            carry = sum / 10;

            if (i != 0)
                result = String.valueOf(sum % 10) + result;
            else
                result = String.valueOf(sum) + result;
        }

        return result;
    }
}
{% endcodeblock %}