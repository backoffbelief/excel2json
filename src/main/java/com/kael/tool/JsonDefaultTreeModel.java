package com.kael.tool;

import java.util.Map.Entry;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonDefaultTreeModel extends DefaultTreeModel {


	private JsonDefaultTreeModel(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
	}

	private JsonDefaultTreeModel(TreeNode root) {
		super(root);
	}

	public JsonDefaultTreeModel(TreeNode root, boolean asksAllowsChildren,
			Object obj) {
		this(root, asksAllowsChildren);
		if (obj == null) {
			throw new RuntimeException("");
		}
		
		((DefaultMutableTreeNode)root).add(createTreeNode(obj, "JSON"));
	}

	public JsonDefaultTreeModel(TreeNode root, JSONObject obj) {
		this(root, true, obj);
	}

	private DefaultMutableTreeNode createTreeNode(Object obj, String key) {
		if (obj == null) {
			return null;
		}
		DefaultMutableTreeNode result = null;
		if (obj instanceof JSONArray) {
			result = createTreeNode4Array((JSONArray)obj, "[]");
		} else if (obj instanceof JSONObject) {
			result = createTreeNode4Obj((JSONObject)obj, key);
		}else{
			result = new DefaultMutableTreeNode("\""+key+"\":"+obj);
		}
		return result;
	}
	
	

	private DefaultMutableTreeNode createTreeNode4Array(JSONArray array,
			String parentKey) {
		if (array == null) {
			return null;
		}
		DefaultMutableTreeNode result = new DefaultMutableTreeNode(parentKey);
		for (int i = 0; i < array.size(); i++) {
			result.add(createTreeNode(array.get(i), "["+i+"]"));
		}
		return result;
	}
	
	private DefaultMutableTreeNode createTreeNode4Obj(JSONObject obj,
			String parentKey) {
		if (obj == null) {
			return null;
		}
		DefaultMutableTreeNode result = new DefaultMutableTreeNode("\""+parentKey+"\"");
		Set<Entry<String,Object>> entrySet = obj.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			result.add(createTreeNode(entry.getValue(),entry.getKey()));
		}
		return result;
	}
}
