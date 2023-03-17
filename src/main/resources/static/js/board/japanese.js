$(function(){
		
		$('#btnCommentInsert').click(function(){
		
			let jsonData ={
				content : $('#content').val(),
				uid : $('input[name=uid]').val(),
				parent:$('input[name=no]').val()
			};
			
			 $.ajax({
			        type: 'POST',
			        url: '/ChimAcademy/board/P701/comment',
			        data: jsonData,
			        dataType:'json',
			        success: function(comments) {
			        	alert('댓글 작성에 성공했습니다.');
			            $('#commentArea').empty();
			            comments.forEach(function(comment) {
			              var html = `
			                <div class="view write">
			                  <h2>${comment.uid}</h2>
			                  <p>${comment.content}</p>
			                  <span>${comment.rdate}</span>
			                  <div class="btn_end">
			              `;
			              if (comment.uid == $('input[name=uid]').val()) {
			                html += `
			                  <button class='btn_small btn_comment'>수정</button>
			                  <button class='btn_small btn_comment delete' th:data-no="${comment.no}">삭제</button>
			                `;
			              }
			              html += `
			                    <button class="btn_small"><i class="fas fa-thumbs-up"> 200</i></button>
			                    <button class="btn_small"><i class="fas fa-thumbs-down"> 10</i></button>
			                  </div>
			                </div>
			              `;
			              $('#commentArea').append(html);
			            });
			       	 }
			     });			 
			
			 
		});
		
		 $('.delete').on('click',function(){
				alert('삭제');
				let no = $(this).data('no');
				 $.ajax({
				        type: 'GET',
				        url: '/ChimAcademy/board/P701/commentDelete?no='+no,
				        /* data: jsonData, */
				        /* dataType:'json', */
				        success: function(comments) {
				        	alert('댓글 삭제에 성공했습니다.');
				        	location.reload();
/* 				            $('#commentArea').empty();
				            comments.forEach(function(comment) {
				              var html = `
				                <div class="view write">
				                  <h2>${comment.uid}</h2>
				                  <p>${comment.content}</p>
				                  <span>${comment.rdate}</span>
				                  <div class="btn_end">
				              `;
				              if (comment.uid == $('input[name=uid]').val()) {
				                html += `
				                  <button class='btn_small btn_comment'>수정</button>
				                  <button class='btn_small btn_comment delete'>삭제</button>
				                `;
				              }
				              html += `
				                    <button class="btn_small"><i class="fas fa-thumbs-up"> 200</i></button>
				                    <button class="btn_small"><i class="fas fa-thumbs-down"> 10</i></button>
				                  </div>
				                </div>
				              `;
				              $('#commentArea').append(html);
				            }); */
				       	 }
				     });		
		})
		
	}); 

	
