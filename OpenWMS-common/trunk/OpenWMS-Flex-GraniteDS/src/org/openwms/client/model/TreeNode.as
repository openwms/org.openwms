package org.openwms.client.model
{
	import mx.collections.ArrayCollection;
	import flash.utils.Dictionary;
	import mx.controls.Alert;
	import org.openwms.common.domain.LocationGroup;
	
	public class TreeNode
	{
		private var _parent:TreeNode;
		private var _child:ArrayCollection;
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
		private function getParent():TreeNode {
			return _parent;
		}
		
		public function getChild(key:Object):TreeNode {
			if(_child==null) {
				return null;
			}
			for (var i:uint=0;i<_child.length;i++) {
				if (_child[i].getData().id == key){
					return _child[i];
				}
			}
			return null;
		}
		private function addChild(node:TreeNode):void {
			if (_child==null) {
				_child = new ArrayCollection();
			}
			_child.addItem(node);
		}
				
		public function get children():ArrayCollection {
			return _child;
		}
		
		
		private function createTree(root:TreeNode):TreeNode {
			var r:TreeNode;
			for (var i:uint=0;i < _list.length; i++) {
				r = searchForNode(_list[i], root);
			}
			trace("----Start tracing tree----");
			traceTree(root,0);
			return root;
		}

		private function searchForNode(lg:LocationGroup, root:TreeNode):TreeNode {
			trace("Node details :"+lg.name+", Parent:"+lg.parent," Root:"+root.getData());
			var node:TreeNode;
			if (lg.parent == null) {
				trace("--Parent == null");
				node = root.getChild(lg.id);
				if (node == null) {
					trace("--Parent has no child set");
					var n1:TreeNode = new TreeNode();
					n1.setData(lg);
					n1.setParent(root);
					root.addChild(n1);
					trace("--n1 looks like:["+n1.getParent()+"], ["+n1.getData()+"]");
					trace("--Adding n1 as child of root:");
					return n1;
				} else {
					trace("--Child of it:"+node.getData());
				}
				return node;
			} else {
				trace("Parent is not null, Parent ="+lg.parent.name);
				node = searchForNode(lg.parent, root);
				trace("After coming back from search for "+lg.parent);
				var child:TreeNode = node.getChild(lg.id);
				if (child == null) {
					child = new TreeNode();
					child.setData(lg);
					child.setParent(node);
					node.addChild(child);
					trace("Added lg as child of node");
				}
				return child;
			}
		}

		private function traceTree(tree:TreeNode, level:uint):void {
			//trace("start trace");
			if (tree != null) {
				var _level:uint=level+1;
				var l:String="";
				for(var i:uint=0;i<_level;i++) {
					l=l+"-";
				}
				if (tree.getData() != null) {
					trace(l+""+tree.getData().name);
				}
				var f:ArrayCollection=tree.children;
				if (f != null) {
					//trace(l+"Tracing Children:"+f.length);
					for (var x:uint;x<f.length;x++) {
						traceTree(f[x],_level);
					}
				}
			}
		}
		

	}
}