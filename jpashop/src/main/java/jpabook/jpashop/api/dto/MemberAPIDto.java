package jpabook.jpashop.api.dto;

import javax.validation.constraints.NotEmpty;

public class MemberAPIDto {

	public static class CreateMemberRequest {
		@NotEmpty
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class CreateMemberResponse {
		private Long id;

		public CreateMemberResponse(Long id) {
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

	public static class UpdateMemberRequest {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class UpdateMemberResponse {
		private Long id;
		private String name;

		public UpdateMemberResponse(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class MemberResponse {
		private String name;

		public MemberResponse(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
