
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class TextFrame extends JFrame{

    public TextFrame()
    {
        super("简易文本编辑器");    //调用父类的构造方法
        //创建菜单栏(JMenuBar)对象
        JMenuBar mBar = new JMenuBar();
        //在JFrame等容器中设置菜单栏对象，即将菜单栏添加到框架容器中
        this.setJMenuBar(mBar);

        //创建菜单
        JMenu file = new JMenu("文件");
        JMenu edit = new JMenu("编辑");
        JMenu count = new JMenu("统计");
        JMenu form = new JMenu("格式");
        JMenu help = new JMenu("帮助");

        //将菜单添加到菜单栏中
        mBar.add(file);
        mBar.add(edit);
        mBar.add(count);
        mBar.add(form);
        mBar.add(help);

        JTextArea workArea = new JTextArea();  //创建多行文本框
        JScrollPane imgScrollPane = new JScrollPane(workArea);  //创建一个空视图，只要组件内容超过视图大小就会显示水平和垂直滚动条
        add(imgScrollPane,BorderLayout.CENTER);  //将当前类的对象实例加到frame的中间位置

        //定义打开和保存对话框
        FileDialog openDia;
        FileDialog saveDia;
        //默认模式为 FileDialog.LOAD
        openDia = new FileDialog(this,"打开",FileDialog.LOAD);
        saveDia = new FileDialog(this,"另存为",FileDialog.SAVE);

        JMenuItem item1_1 = new JMenuItem("新建");  //生成一个对象
        item1_1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  //对当前的控件添加监听器，点击控件时就会触发监听函数里面的内容
                workArea.setText("");  //清空文本
            }
        });
        JMenuItem item1_2 = new JMenuItem("打开");
        item1_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  //对当前的控件添加监听器，点击控件时就会触发监听函数里面的内容
                openDia.setVisible(true);  //打开文件对话框
                String dirPath = openDia.getDirectory();  //获取打开文件路径并保存
                String fileName = openDia.getFile();  //获取文件名称并保存
                //判断打开路径或目录是否为空，则返回空
                if(dirPath == null || fileName == null){
                    return ;
                }
                workArea.setText("");//清空文本
                File fileO = new File(dirPath,fileName);
                try{
                    BufferedReader bufr = new BufferedReader(new FileReader(fileO));      //尝试从文件中读取内容
                    String line = null;  //变量字符串初始化为空
                    while((line = bufr.readLine()) != null){
                        workArea.append(line + "\r\n");  //显示每行内容
                    }
                    bufr.close();   //关闭文本
                }
                catch(IOException er1){
                    throw new RuntimeException("文件读取失败！");
                }
            }
        });

        JMenuItem item1_3 = new JMenuItem("保存");
        item1_3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
                File fileS = null;
                if(fileS == null){
                    saveDia.setVisible(true);  //显示保存文件对话框
                    String dirPath = saveDia.getDirectory();  //获取保存文件路径并保存到字符串中
                    String fileName = saveDia.getFile();  //获取保存文件名称并保存到字符串中

                    if(dirPath == null || fileName == null)  //判断路径和文件是否为空
                        return;  //返回空值

                    fileS = new File(dirPath,fileName);  //文件不为空，新建一个路径和名称
                }
                try{
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));                           //尝试从文件中读取内容
                    String text = workArea.getText();  //获取文本内容
                    bufw.write(text);  //将获取文本内容写入到字符输出流
                    bufw.close();  //关闭文件
                }catch(IOException er){
                    throw new RuntimeException("文件保存失败！");
                }
            }
        });

        JMenuItem item1_4 = new JMenuItem("另存为");
        item1_4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){   //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
                File fileS = null;
                if(fileS == null){
                    saveDia.setVisible(true);  //显示另存为文件对话框
                    String dirPath = saveDia.getDirectory();  //获取保存文件路径并保存到字符串中
                    String fileName = saveDia.getFile();  //获取保存文件名称并保存到字符串中

                    if(dirPath == null || fileName == null) //判断路径和文件是否为空
                        return ;   //返回空值
                    fileS = new File(dirPath,fileName);  //文件不为空，新建一个路径和名称
                }
                try{
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));                          //尝试从文件中读取内容
                    String text = workArea.getText();  //获取文本内容
                    bufw.write(text);   //将获取文本内容写入到字符输出流
                    bufw.close();  //关闭文件
                }
                catch(IOException er){
                    throw new RuntimeException("文件保存失败！");
                }
            }
        });

        JMenuItem item1_5 = new JMenuItem("退出");
        item1_5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
                System.exit(0);
            }
        });

        //创建菜单项
        JMenuItem item2_1 = new JMenuItem("剪切");
        item2_1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                workArea.cut();
            }
        });

        JMenuItem item2_2 = new JMenuItem("复制");
        item2_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                workArea.copy();
            }
        });

        JMenuItem item2_3 = new JMenuItem("粘贴");
        item2_3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                workArea.paste();
            }
        });

        JMenuItem item2_4 = new JMenuItem("统计");
        item2_4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
                //try代码块，当发生异常时会转到catch代码块中
                String a = workArea.getText();//定义字符串变量，并赋值为用户输入的信息
                //创建类进行文件的读取，并指定编码格式为utf-8
                //char[] ch = a.toCharArray();//把字符串变成char数组
                int c = 0,h = 0,ll = 0,l = 0,n = 0,o = 0,sum = 0;//定义整型变量，用于统计字符数
                for(int i = 0;i<a.length();i++){
                    String s = a.substring(i,i+1);
                    if (s.matches("[\\u4e00-\\u9fa5]")) {//if语句的条件，判断是否为汉字
                        h++;//若为汉字则c1自增
                    } else if(s.matches("[A-Z]")){//if语句的条件，判断是否为大写字母
                        ll++;//若为大写字母则c2自增
                    } else if(s.matches("[a-z]")){//if语句的条件，判断是否为小写字母
                        l++;//若为小写字母则c3自增
                    } else if(s.matches("[0-9]")){//if语句的条件，判断是否为数字
                        n++;//若为数字则c4自增
                    } else {//否则可判断为其他字符
                        o++;//若为其他字符则c5自增
                    }
                }
                sum = h + ll + l + n + o ;//统计总字符数
                JOptionPane.showMessageDialog(TextFrame.this, "字数统计：\n汉字："+h+"\n大写字母："+ll+"\n小写字母:"+l+"\n数字："+n+"\n其他字符："+o+"\n共计"+sum);
            }
        });


        JRadioButtonMenuItem item3_1 = new JRadioButtonMenuItem("自动换行",false);
        item3_1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
                Object source = e.getSource();
                if(source == item3_1)
                    workArea.setLineWrap(true);  //自动换行
                else if(source != item3_1)
                    workArea.setLineWrap(false);
            }
        });

        JMenuItem item4_1 = new JMenuItem("帮助");
        item4_1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
                new Help();
            }
        });
        JMenuItem item4_2 = new JMenuItem("关于");
        item4_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){  //对当前的控件添加一个监听器，点击控件时就会触发监听函数里面的内容
                new About ();
            }
        });

        //在菜单中添加菜单项
        file.add(item1_1);file.add(item1_2);file.add(item1_3);file.add(item1_4);file.add(item1_5);
        edit.add(item2_1);edit.add(item2_2);edit.add(item2_3);
        form.add(item3_1);
        help.add(item4_1);help.add(item4_2);


    }//构造方法结束

    public static void main(String args[])
    {
        TextFrame app = new TextFrame();

        app.setSize(600, 400);  //设置窗口大小,宽度600，高度400
        app.setLocation(200,200);  //设置窗口位置为距离屏幕左边水平方向200，上方垂直方向200
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //点击关闭按钮是直接退出
        app.setVisible(true);  //设置窗体可见
    }
}