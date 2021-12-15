import javax.swing.*;

public class About extends JFrame{
    public About(){
        super("关于"); //调用父类的构造方法
        this.setSize(280, 100);  //设置窗口大小,宽度280，高度100
        this.setLocation(250,300);  //设置窗口位置为距离屏幕左边水平方向250，上方垂直方向300
        this.setResizable(false);  //设置窗体大小不可改变
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //点击关闭按钮直接退出
        this.setVisible(true);  //设置窗体可见
        JPanel panel = new JPanel();  //使用默认的浮动布局
        JLabel label1 = new JLabel("这是一个用Java开发的简易文本编译器。");
        JLabel label2 = new JLabel("Copyright ©2020 马桂雨");

        panel.add(label1);
        panel.add(label2);
        this.add(panel);
    }
}
