
import javax.swing.*;
import java.awt.*;

public class Help extends JFrame{

    public Help(){
        super("帮助");  //调用父类的构造方法
        this.setSize(500, 150);  //设置窗口大小,宽度500，高度150
        this.setLocation(250,300);  //设置窗口位置为距离屏幕左边水平方向250，上方垂直方向300
        this.setResizable(false);  //设置窗体大小不可改变
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //点击关闭按钮是直接退出
        this.setVisible(true);  //设置窗体可见

        Container c = this.getContentPane();  //初始化一个容器
        c.setLayout(new GridLayout(3,0));   //将窗口布局设置为网格式布局，网格的行数和列数分别是3和0

        //创建标签文字
        JLabel label1 = new JLabel("1、“菜单”中的功能“新建”、“打开”、“保存”、“另存为”、“退出”通过点击对应使用。");
        JLabel label2 = new JLabel("2、“编辑”中的“剪切”“复制”“粘贴”可对应使用快捷键Ctrl + X、Ctrl + C、Ctrl +V。");
        JLabel label3 = new JLabel("3、“格式”里的“自动换行”勾选后到窗口边缘自动换行；不勾选则向后继续写入。");
        c.add(label1);c.add(label2);c.add(label3);  //添加标签到容器中
    }
}