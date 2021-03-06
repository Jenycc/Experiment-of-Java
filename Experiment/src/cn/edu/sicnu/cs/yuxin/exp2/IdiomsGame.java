package cn.edu.sicnu.cs.yuxin.exp2;

import java.util.*;

public class IdiomsGame {
    private final static String[] idioms = {"凉拌黄瓜", "明明白白", "马到成功", "从中作梗", "舍我其谁", "传诵一时"};
    private final static String[] chars = {"王", "根", "基", "你", "要", "努", "力", "学", "习", "做", "一", "个", "好", "人", "不", "要", "干", "坏", "事", "儿"};

    public static void main(String[] args) {
        Run();
    }

    public static void Run() {  //游戏运行函数
        Scanner input = new Scanner(System.in);
        String[] charbuffer = new String[10];
        boolean[] charbufferflag = new boolean[10];
        Random random = new Random(System.currentTimeMillis());
        int index = Math.abs(random.nextInt()) % idioms.length;
        String idiom = idioms[index];
        int indeb = 0;
        int n = 0;
        boolean[] rflag = {false, false, false, false};
        boolean flag = true;
        int c = 0;
        int count = 0;
        boolean fflag = false;
        for (int i = 0; i < idiom.length() - 1; i++) {   // 查找重复字，并放入charbuffer字符数组中
            for (int k = i + 1; k < idiom.length(); k++) {
                if (idiom.substring(i, i + 1).equals(idiom.substring(k, k + 1))) {
                    charbuffer[c++] = idiom.substring(i, i + 1);
                    fflag = true;                           // 存在重复字符
                }
            }
        }

        if (!fflag) {                                       // 没有重复字符，将成语里面的所有字符放入数组
            for (int i = 0; i < idiom.length(); i++) {
                charbuffer[i] = idiom.substring(i, i + 1);
            }
        }

        Set<Integer> in = new HashSet<>();   // 随机产生0-chars.length范围内的10 - (4 - c)个整数
        while (in.size() < (10 - (4 - c))) {
            in.add(Math.abs(random.nextInt()) % chars.length);
        }

        c = 4 - c;
        for (int i : in) {      // 将随机取到的整数放入charbuffer
            charbuffer[c++] = chars[i];
        }

        int[] charCode = new int[charbuffer.length];
        Arrays.fill(charCode, -1);

        for (int i = 0; i < charCode.length; i++) {  //随机产生10个0-9的整数
            int temp = random.nextInt(10);
            charCode[i] = temp;
        }

        for (int i = 0; i < charCode.length - 1; i++) { //通过对整形数组排序打乱字符数组顺序
            for (int k = i + 1; k < charCode.length; k++) {
                if (charCode[i] > charCode[k]) {
                    String temp = charbuffer[i];
                    charbuffer[i] = charbuffer[k];
                    charbuffer[k] = temp;
                    int tmp = charCode[i];
                    charCode[i] = charCode[k];
                    charCode[k] = tmp;
                }
            }
        }
        // 以下开始游戏
        System.out.println("四字成语中包含的汉字如下：");
        for (int i = 0; i < charbuffer.length; i++) {
            System.out.print(i + 1 + "." + charbuffer[i] + ";");
        }
        for (int i = 0; i < 6; i++) {
            if (flag) {
                System.out.print("\n[");
                for (int k = 0; k < idiom.length(); k++) {
                    if (rflag[k]) {
                        System.out.print(idiom.substring(k, k + 1));
                    } else {
                        System.out.print("O");
                    }
                }
                System.out.print("]\n");
                flag = false;
            }
            if (i == 0) {
                System.out.print("剩余猜测次数为" + (6 - i) + "次，请输入你的猜测：");
            } else {
                System.out.print("剩余猜测次数为" + (6 - i) + "次，请再次输入你的猜测：");
            }
            do {
                n = input.nextInt();
                if (n < 1 || n > charbuffer.length) {
                    System.out.print("你的输入有误！剩余猜测次数为" + (6 - i) + "次，请再次输入你的猜测：");
                } else {
                    break;
                }
            } while (true);
            index = idiom.indexOf(charbuffer[n - 1]);
            indeb = idiom.indexOf(charbuffer[n - 1], index + 1);
            if (index != -1) {
                rflag[index] = true;
                if (indeb != -1) {
                    rflag[indeb] = true;
                }
                if (charbufferflag[n - 1]) {
                    System.out.print("对不起，“" + charbuffer[n - 1] + "”字你已经猜过了，");
                } else {
                    System.out.print("[");
                    for (int k = 0; k < idiom.length(); k++) {
                        if (rflag[k]) {
                            System.out.print(idiom.substring(k, k + 1));
                        } else {
                            System.out.print("O");
                        }
                    }
                    System.out.print("]\n");
                    System.out.print("恭喜你，“" + charbuffer[n - 1] + "”字在成语中的位置是：" + (index + 1));
                    if (indeb != -1) {
                        System.out.print("、" + (indeb + 1));
                    }
                    System.out.println();
                }
            } else {
                count++;
                if (charbufferflag[n - 1]) {
                    System.out.print("对不起，“" + charbuffer[n - 1] + "”字你已经猜过了，");
                } else {
                    System.out.print("对不起，“" + charbuffer[n - 1] + "”字不再成语中，");
                }
                if (i == 5) {
                    System.out.print("并且你已经Game Over了！");
                    return;
                }
            }
            charbufferflag[n - 1] = true;
            if (rflag[0] && rflag[1] && rflag[2] && rflag[3]) {
                System.out.print("太棒了！你一共猜错了" + count + "次，已经猜出了整个成语");
                break;
            } else if (i == 5) {
                System.out.print("很遗憾！你一共猜错了" + count + "次，你已经Game Over了！");
            }
        }
    }
}
