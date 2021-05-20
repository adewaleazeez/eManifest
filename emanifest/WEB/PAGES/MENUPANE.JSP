	<link rel="stylesheet" href="css/demo.css"></link>
	
	<script type="text/javascript" src="js/chili-1.7.pack.js"></script>

	<script type="text/javascript" src="js/jquery.easing.js"></script>
	<script type="text/javascript" src="js/jquery.dimensions.js"></script>
	<script type="text/javascript" src="js/jquery.accordion.js"></script>
	<script>
		jQuery().ready(function(){
			jQuery('#navigation').accordion({ 
				active: false, 
				header: '.head', 
				navigation: true, 
				event: 'mouseover', 
				fillSpace: true, 
				animated: 'bounceslide' 
			});
		});
	</script>
	<ul id="navigation">
		<li>
			<a class="head" href="#">Home</a>
			<ul>
				<li>
					<a href="home.php" title="Home">Home</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
			</ul> 
		</li>
		<li>
			<a class="head" href="#">Store Items</a>
			<ul>
				<li>
					<a href="javascript:checkAccess('items.php','Raw Materials')" title="Manage store items">Materials</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
			</ul> 
		</li>
		<li>
			<a class="head" href="#">Production Items</a>
			<ul>
				<li>
					<a href="javascript:checkAccess('productions.php','Production Items')" title="Manage production items">Production Items</a>
				</li>
				<li>
					<a href="javascript:checkAccess('updateproduct.php','Update Production Status')" title="Update Production Status">Update Production</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
			</ul>
		</li>
		<li>
			<a class="head" href="#">Stock Items</a>
			<ul>
				<li>
					<a href="javascript:checkAccess('addstock.php','Add To Stock')" title="Add items to stock">Add To Stock</a>
				</li>
				<li>
					<a href="javascript:checkAccess('removestock.php','Remove From Stock')" title="Remove items from stock">Remove From Stock</a>
				</li>
				<li>
					<a href="javascript:checkAccess('updatestock.php','Update Stock Status')" title="Update stock items">Update Stock Status</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
			</ul>
		</li>
		<li>
			<a class="head" href="#">Manage Users</a>
			<ul>
				<li>
					<a href="javascript:checkAccess('usersmanager.php','Manage Users')" title="Add/Edit Users">Add/Edit Users</a>
				</li>
				<li>
					<a href="javascript:checkAccess('changepassword.php','Change Password')" title="Chage User Password">Change Password</a>
				</li>
				<li>
					<a href="javascript:checkAccess('usersmenu.php','User Menu Access')" title="User Menu Access">User Menu Access</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
			</ul>
		</li>
		<li>
			<a class="head" href="#">Reports</a>
			<ul>
				<li>
					<a href="javascript:checkAccess('Productionreport.php','Production Items Report')" title="Production Items Report">Production Reports</a>
				</li>
				<li>
					<a href="javascript:checkAccess('stockreports.php','Stock Reports')" title="Stock Items Reports">Stock Report</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
			</ul>
		</li>
		<li>
			<a class="head" href="#">Logout</a>
			<ul>
				<li>
					<a href="login.php" title="Logout">Logout</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
				<li>
					<a href="#">&nbsp;</a>
				</li>
			</ul> 
		</li>
	</ul>
