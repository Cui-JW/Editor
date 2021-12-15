import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class Editor extends JFrame implements ActionListener, DocumentListener {

    public JEditorPane textPane;
    public boolean changed = false;
    private JMenuBar menu;
    private JMenuItem copy, paste, cut, about;
    private File file;

    public Editor() {
        super("Editor");
        setTitle("文本编辑器");
        textPane = new JEditorPane();
        getContentPane().add(new JScrollPane(textPane), BorderLayout.CENTER);
        textPane.getDocument().addDocumentListener(this);

        menu = new JMenuBar();
        Toolkit tool=menu.getToolkit(); //得到一个Toolkit对象
        Image icon=tool.getImage("./img.png"); //由tool获取图像
        this.setIconImage(icon);
        setJMenuBar(menu);
        buildMenu();
        setSize(1000, 650);
        setLocation(420, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new Editor();


    }

    private void buildMenu() {
        buildFileMenu();
        buildEditMenu();
        buildHelp();
    }

    private void buildFileMenu() {
        JMenu file = new JMenu("文件(F)");
        file.setFont(new Font("黑体", Font.PLAIN, 15));
        file.setMnemonic('F');
        menu.add(file);
        JMenuItem n = new JMenuItem("新建");
        n.setMnemonic('N');
        n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        n.addActionListener(this);
        file.add(n);
        JMenuItem open = new JMenuItem("打开");
        file.add(open);
        open.addActionListener(this);
        open.setMnemonic('O');
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        JMenuItem save = new JMenuItem("保存");
        file.add(save);
        save.setMnemonic('S');
        save.addActionListener(this);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        JMenuItem saveAs = new JMenuItem("另存为...");
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        file.add(saveAs);
        saveAs.addActionListener(this);
        JMenuItem quit = new JMenuItem("退出");
        file.add(quit);
        quit.addActionListener(this);
        quit.setMnemonic('Q');
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
    }

    private void buildEditMenu() {
        JMenu edit = new JMenu("编辑(E)");
        edit.setFont(new Font("黑体", Font.PLAIN, 15));
        menu.add(edit);
        edit.setMnemonic('E');
        // cut
        cut = new JMenuItem("剪切");
        cut.addActionListener(this);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        cut.setMnemonic('T');
        edit.add(cut);
        // copy
        copy = new JMenuItem("复制");
        copy.addActionListener(this);
        copy.setMnemonic('C');
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        edit.add(copy);
        // paste
        paste = new JMenuItem("粘贴");
        paste.setMnemonic('P');
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        edit.add(paste);
        paste.addActionListener(this);
        // find
        JMenuItem find = new JMenuItem("查找");
        find.setMnemonic('F');
        find.addActionListener(this);
        edit.add(find);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        // select all
        JMenuItem sall = new JMenuItem("选择全部");
        sall.setMnemonic('A');
        sall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        sall.addActionListener(this);
        edit.add(sall);
    }


    public void buildHelp(){
        JMenu help = new JMenu("帮助(H)");
        help.setFont(new Font("黑体", Font.PLAIN, 15));
        help.setMnemonic('H');
        menu.add(help);

        about = new JMenuItem("关于记事本");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        about.addActionListener(this);
        help.add(about);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("退出")) {
            System.exit(0);
        } else if (action.equals("打开")) {
            loadFile();
        } else if (action.equals("保存")) {
            saveFile();
        } else if (action.equals("新建")) {
            newFile();
        } else if (action.equals("另存为...")) {
            saveAs("另存为...");
        } else if (action.equals("选择全部")) {
            textPane.selectAll();
        } else if (action.equals("复制")) {
            textPane.copy();
        } else if (action.equals("剪切")) {
            textPane.cut();
        } else if (action.equals("粘贴")) {
            textPane.paste();
        } else if (action.equals("查找")) {
            FindDialog find = new FindDialog(this, true);
            find.showDialog();
        } else if (action.equals("关于记事本")){
            help();
        }
    }

    private void newFile() {
        if (changed)
            saveFile();
        file = null;
        textPane.setText("");
        changed = false;
        setTitle("Editor");
    }

    private void loadFile() {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setMultiSelectionEnabled(false);
        try {
            int result = dialog.showOpenDialog(this);
            if (result == JFileChooser.CANCEL_OPTION)
                return;
            if (result == JFileChooser.APPROVE_OPTION) {
                if (changed)
                    saveFile();
                file = dialog.getSelectedFile();
                textPane.setText(readFile(file));
                changed = false;
                setTitle("Editor - " + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr);) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot read file !", "Error !", JOptionPane.ERROR_MESSAGE);
        }
        return result.toString();
    }

    private void saveFile() {
        if (changed) {
            int ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ans == JOptionPane.NO_OPTION)
                return;
        }
        if (file == null) {
            saveAs("Save");
            return;
        }
        String text = textPane.getText();
        System.out.println(text);
        try (PrintWriter writer = new PrintWriter(file);) {
            if (!file.canWrite())
                throw new Exception("Cannot write file!");
            writer.write(text);
            changed = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAs(String dialogTitle) {
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION)
            return;
        file = dialog.getSelectedFile();
        try (PrintWriter writer = new PrintWriter(file);) {
            writer.write(textPane.getText());
            changed = false;
            setTitle("Editor - " + file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void help(){
        StringBuilder msg = new StringBuilder();
        msg.append("<html>关于 Stack 记事本");
        msg.append("<br />——————————————————<br />");
        msg.append("<center> 开发人员列表 </center>");
        msg.append("<center> 王鹏杰 </center>");
        msg.append("<center> 冯重伟 </center>");
        msg.append("<center> 刘纪宏 </center>");
        msg.append("<center> 崔建文 </center>");
        msg.append("<br /><br />Copyright ©2021 Stack Develop Group</html>");
        System.out.println(msg);
        JOptionPane.showMessageDialog(null, msg, "关于记事本", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changed = true;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changed = true;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changed = true;
    }

}