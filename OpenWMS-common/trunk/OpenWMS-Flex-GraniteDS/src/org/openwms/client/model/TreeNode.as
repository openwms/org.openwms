package org.openwms.client.model
{
	import mx.collections.ArrayCollection;
	import flash.utils.Dictionary;
	import mx.controls.Alert;
	import org.openwms.common.domain.LocationGroup;
	
	public class TreeNode
	{
		private var _parent:TreeNode;
		private var _child:Dictionary = new Dictionary();
		private var _data:Object;
		private var _list:ArrayCollection;

		public function TreeNode(){}
		
		public function build(list:ArrayCollection):void {			
			_list = list;
			createTree(this);
		}			
		
		public function setData(obj:Object):void {
			_data = obj;
		}
		
		public function getData():Object {
			return _data;
		}
		
		public function setParent(node:TreeNode):void {
			_parent = node;
		}
		
		public function getChild(key:Object):TreeNode {
			Alert.show("Try to find child with key:"+key);
			Alert.show("Details of arr:"+_child);
			return _child[key];
		}
		
		
		private function createTree(root:TreeNode):TreeNode {
			for (var i:uint=0;i < _list.length; i++) {
				searchForNode(_list[i], root);
			}
			return root;
		}

		private function searchForNode(lg:LocationGroup, root:TreeNode):TreeNode {
			Alert.show("Node details :"+lg.name+", Parent:"+lg.parent);
			trace("okay");
			var node:TreeNode;
			if (lg.parent == null) {
				Alert.show("I understand, parent is null");
				node = root.getChild(lg.id);
				Alert.show("Okay, parent found, node = "+node);
				if (node == null) {
					Alert.show("Add this as root node");
					var n1:TreeNode = new TreeNode();
					n1.setData(lg);
					n1.setParent(root);
					root.addChild(n1.getData().id, n1);
					Alert.show("Return new node n1:"+n1);
					return n1;
				}
				return node;
			} else {
				node = searchForNode(lg.parent, root);
				var child:TreeNode = node.getChild(lg.id);
				if (child == null) {
					child = new TreeNode();
					child.setData(lg);
					child.setParent(node);
					node.addChild(lg.id, child);
				}
				return child;
			}
		}

		private function addChild(key:Object, node:TreeNode):void {
			_child[key] = node;
		} 
		

	}
}