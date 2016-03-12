package com.kael.tool;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonViewer extends JFrame{  
      
    Box box;  
    JTree jTree1;  
    JTabbedPane jTabbedPane;
    
    JTextArea text;
      
    public JsonViewer()  
    {  
    	text = new JTextArea();
    	jTabbedPane = new JTabbedPane();
    	jTabbedPane.addTab("json", null, text, null);
    	
    	JSONObject obj = new JSONObject();
    	obj.put("id", 1);
    	List list = Arrays.asList(1,2,3,4,5,6);
    	obj.put("nums",new JSONArray(list));
    	/**obj.put("nums",new JSONObject(map));*/
    	
    	Hashtable<String, Object> child = new Hashtable<String, Object>();
    	child.put("cid", 2);
    	child.put("cidA", "1111");
    	child.put("cidB", "aaadadsad");
    	obj.put("child",child);
    	String s = JSON.toJSONString(obj);
    	
       
        box = Box.createHorizontalBox(); //创建Box 类对象  
//        jTree1 = new JTree();  
        jTree1 = new JTree(new JsonDefaultTreeModel(new DefaultMutableTreeNode(), JSON.parseObject(s)));  
//        jTree2 = new JTree();  
          
        //向此组件添加任意的键/值  
        jTree1.putClientProperty("JTree.lineStyle", "Angled");   
          
        jTabbedPane.addTab("视图", null, new JScrollPane(jTree1), null);
        
        //向Box 容器添加滚动面板  
        box.add(new JScrollPane(jTabbedPane));  
//        box.add(new JScrollPane(jTree2), BorderLayout.EAST);  
        getContentPane().add(box, BorderLayout.CENTER);  
          
        setSize(300, 240);  
        //f.pack();  
       setLocation(300, 200);  
        setVisible(true);  
        setTitle("JsonViewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
      
    public static void main(String[] args) { 
    	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new JsonViewer();  
				} catch (Exception e) {
				}
			}
		});
        
    } 
    
}