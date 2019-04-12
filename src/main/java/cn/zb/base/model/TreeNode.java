package cn.zb.base.model;

import java.io.Serializable;
import java.util.List;

import cn.zb.base.entity.HierarchyableEntity;

public class TreeNode<T extends HierarchyableEntity<?>> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3611136046977018336L;
	
	

	public TreeNode(T value) {
		this.value = value;
	}

	private T value;

	private List<TreeNode<T>> childrens;

	public List<TreeNode<T>> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<TreeNode<T>> childrens) {
		this.childrens = childrens;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
