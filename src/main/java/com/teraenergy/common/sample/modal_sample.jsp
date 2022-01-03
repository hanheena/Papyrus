<div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="모달창id">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">모달창 제목</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <span aria-hidden="true" onclick="$('#모달창id').modal('hide');">닫기</span>
          </button>
        </div>

        <div class="modal-body">
			
			적고싶은 내용

		<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</div>

<button onclick="$('#모달창 id').modal('show');">모달창 띄우기 버튼</button>


만약에 모달에 모달창을 띄우고 싶으면 (예: 모달창 1 -> 모달창 2)

<div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="모달창id">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">모달창 제목</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <span aria-hidden="true" onclick="$('#모달창id').modal('hide');">닫기</span>
          </button>
        </div>

        <div class="modal-body">
			
			모달창1

		<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</div>

<div class="modal fade schedule-modal" tabindex="-1" role="dialog" id="모달창id">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">모달창 제목</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <span aria-hidden="true" onclick="$('#모달창id').modal('hide');">닫기</span>
          </button>
        </div>

        <div class="modal-body">
			
			모달창2
			
			<button onclick="$('#모달창1 id').modal('show');">모달창 띄우기 버튼</button>

		<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</div>


이렇게 모달창 html 코드도 순서를 지켜줘야함