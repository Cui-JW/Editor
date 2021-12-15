import javax.swing.*;

public class About extends JFrame{
    public About(){
        super("关于"); //调用父类的构造方法
        this.setSize(280, 280);  //设置窗口大小,宽度280，高度100
        this.setLocation(250,300);  //设置窗口位置为距离屏幕左边水平方向250，上方垂直方向300
        this.setResizable(false);  //设置窗体大小不可改变
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //点击关闭按钮直接退出
        this.setVisible(true);  //设置窗体可见
        JPanel panel = new JPanel();  //使用默认的浮动布局
        JLabel label1 = new JLabel("用Java Swing开发的简易文本编译器");
        JLabel label2 = new JLabel("<html><br><br><br>    开发人员列表  </html>");
        JLabel label3 = new JLabel("<html><pre>王鹏杰  冯重伟 刘纪宏 崔建文</pre><br><br><br></html>");
        JLabel label4 = new JLabel("<html><br><br>Copyright ©2021 Stack Develop Group</html>");

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        this.add(panel);
    }

    public void showAbout(){
        setVisible(true);
    }
}
