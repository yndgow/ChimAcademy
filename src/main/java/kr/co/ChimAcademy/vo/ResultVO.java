package kr.co.ChimAcademy.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultVO {

	private getLibraryInfo getLibraryInfo;
	
	@Getter
	@Setter
	public class getLibraryInfo {
		private Header header;
        private Body body;
        @Getter
        @Setter
        public class Header {
            private String resultCode;
            private String resultMsg;
        }
        @Getter
        @Setter
        public class Body {
            private Items items;
            private int numOfRows;
            private int pageNo;
            private int totalCount;
            @Getter
            @Setter
            public class Items{
                private ItemVO[] item;
            };
        }
	}
}
