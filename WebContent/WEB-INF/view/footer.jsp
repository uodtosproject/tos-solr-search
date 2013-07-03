		<hr />
        <footer class="span12">
        	<p>&copy; University of Dundee, 2013</p>
        </footer>
       

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="<%= request.getContextPath()%>/resources/js/vendor/bootstrap.min.js"></script>
        <script src="<%= request.getContextPath()%>/resources/js/main.js"></script>
		<script src="<%= request.getContextPath()%>/resources/js/jquery.scrollToTop.min.js"></script>
        <script type="text/javascript">
		$(document).ready(function () {
			$('#myTab a:first').tab('show');

            $(".scroll").scrollToTop(1000);
		});
		</script>
        </div>
    </body>
</html>
