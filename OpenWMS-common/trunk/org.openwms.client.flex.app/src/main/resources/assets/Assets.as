package assets
{
	[Bindable]
	public class Assets
	{
		public function Assets()
		{
		}
        
        [Embed(source="/resources/assets/images/pinvoke/user-add.png")]
        public static var userAdd:Class
        
        [Embed(source="/resources/assets/images/pinvoke/user--delete.png")]
        public static var userDelete:Class
        
        [Embed(source="/resources/assets/images/pinvoke/users-add.png")]
        public static var usersAdd:Class

        [Embed(source="/resources/assets/images/pinvoke/users-delete.png")]
        public static var usersDelete:Class
	}
}
