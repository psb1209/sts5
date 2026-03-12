// ---- 현재 날짜
let current_year;		// 현재 년도
let current_month;		// 현재 월			3 -> 2
let current_date;		// 현재 일
let current_day;		// 현재 요일

document.addEventListener("DOMContentLoaded", function() {
	console.log("DOCUMENT READY!!");
	
	// ---- 오늘 날짜
	let today = new Date();
	let today_year = today.getFullYear();		// 오늘 년도
	let today_month = today.getMonth();			// 오늘 년도(0 ~ 11)
	let today_date = today.getDate();			// 오늘 일
	let today_day = today.getDay();				// 오늘 요일(0 ~ 6, 0:일요일)
	
	// 현재
	setCurrentCalender(today_year, today_month, today_date, today_day);
	
	// 현재(select UI)
	setCurrentYearAndMonthSelectUI();
	
	// 현재(<tr> UI)
	addCalenderTr();
	
	// 현재 일정들 가져오기
	fetchGetCurrentMonthPlans();

	// 이벤트 핸들러 등록
	initEvents();
	
});

// 현재
function setCurrentCalender(year, month, date, day) {
	console.log("setCurrentCalender()");
	
	current_year = year;
	current_month = month;
	current_date = date;
	current_day = day;
	
}

// 현재(select UI)
function setCurrentYearAndMonthSelectUI() {
	console.log("setCurrentYearAndMonthSelectUI()");
	
	document.querySelector('#section_wrap select[name="p_year"]').value = current_year;
	document.querySelector('#section_wrap select[name="p_month"]').value = current_month + 1;
	
}

// 현재(<tr> UI)
function addCalenderTr() {
	console.log("addCalenderTr()");
	
	let thisCalenderStart = new Date(current_year, current_month, 1);
	// let thisCalenderStartDate = thisCalenderStart.getDate();			//	현재 월의 첫 날			1
	let thisCalenderStartDay = thisCalenderStart.getDay();				//	현재 월의 첫 요일
	
	let thisCalenderEnd = new Date(current_year, current_month + 1, 0);		
	let thisCalenderEndDate = thisCalenderEnd.getDate();				// 현재 월의 마지막 날		31
	
	// 달력 구성 날짜 데이터
	let dates = Array();		// 데이터 셋
	let dateCnt = 1;
	for (let i = 0; i < 42; i++) {
		if (i < thisCalenderStartDay || dateCnt > thisCalenderEndDate) {
			dates[i] = 0;
		} else {
			dates[i] = dateCnt;
			dateCnt++;
		}
		
	}
	
	// UI 만들자
	let tableBody = document.querySelector("#table_calender tbody");
	
	let dateIndex = 0;
	for (let i = 0; i < 6; i++) {
		
		if (i >= 5 && dates[dateIndex] == 0) break;
		
		let tr = document.createElement("tr");
		for (let j = 0; j < 7; j++) {
			let td = document.createElement("td");
			
			if (dates[dateIndex] !== 0) {
				
				// 날짜 txt
				let dateDiv = document.createElement('div');
				dateDiv.className = "date";
				dateDiv.textContent = dates[dateIndex];
				td.appendChild(dateDiv);
				
				// 일정 등록 버튼 UI
				let writeDiv = document.createElement('div');		// <div></div>
				let writeLink = document.createElement('a');		// <a class="write" href="#none">write</a>
				writeLink.className = 'write';
				writeLink.href = "#none";
				writeLink.textContent = 'write';
				writeDiv.appendChild(writeLink);					// <div><a class="write" href="#none">write</a></div>
				td.appendChild(writeDiv);

				// 일정 출력 UI
				let planWrap = document.createElement('div');
				planWrap.className = 'plan_wrap';
				planWrap.id = `date_${dates[dateIndex]}`;

				let planList = document.createElement('ul');
				planList.className = "plan";
				planWrap.appendChild(planList);
				td.appendChild(planWrap);
				
			}
			
			tr.appendChild(td);
			dateIndex++;
		}
		
		tableBody.appendChild(tr);
		
	}
	
}

// 이벤트 핸들러 등록
function initEvents() {
	console.log("initEvents()");
	
	document.addEventListener('click', function(event){
		
		// 이전 달
		if (event.target.matches("#section_wrap .btn_pre")) {
			console.log("btn_pre CLICKED!!");
			setPreMonth();
			
		}
		
		// 다음 달
		if (event.target.matches("#section_wrap .btn_next")) {
			console.log("btn_next CLICKED!!");
			setNextMonth();
			
		}
		
		// write 버튼 클릭 시
		if (event.target.matches("#section_wrap a.write")) {
			
			let year = current_year;
			let month = current_month + 1;
			
			let dateElement = event.target.closest("div").parentElement.querySelector("div.date");
			let date = dateElement.textContent.trim();
			
			showWritePlanView(year, month, date);
			
		}
		
		// cancel 버튼 클릭 시
		if (event.target.matches("#write_plan input[value='CANCEL']")) {
					
			hideWritePlanView();
			
		}
		
		// 모달에서 write 버튼 클릭 시
		if (event.target.matches("#write_plan input[value='WRITE']")) {
			console.log('WRITE BUTTON CLICKED!!');
			
			let year = document.querySelector("#write_plan select[name='wp_year']").value;
			let month = document.querySelector("#write_plan select[name='wp_month']").value;
			let date = document.querySelector("#write_plan select[name='wp_date']").value;

			let title = document.querySelector("#write_plan input[name='p_title']").value;
			let body = document.querySelector("#write_plan input[name='p_body']").value;

			fetchWritePlan(year, month, date, title, body);
			
		}
		
	});
	
	document.addEventListener("change", function(event) {
		
		// 년도 변경
		if (event.target.matches("#section_wrap select[name='p_year']")) {
			setMonthBySelectChanged();
		}
		
		// 월 변경
		if (event.target.matches("#section_wrap select[name='p_month']")) {
			setMonthBySelectChanged();
		}
		
	});
	
}

function showWritePlanView(year, month, date) {
	console.log("showWritePlanView()");
	
	document.querySelector('#write_plan select[name="wp_year"]').value = year;
	document.querySelector('#write_plan select[name="wp_month"]').value = month;
	
	setSelectDateOptions(year, month, 'wp_date');
	
	document.querySelector('#write_plan select[name="wp_date"]').value = date;
	
	document.querySelector('#write_plan').style.display = 'block';
	
}

function hideWritePlanView() {
	console.log("hideWritePlanView()");
	
	document.querySelector('#write_plan input[name="p_title"]').value = '';
    document.querySelector('#write_plan input[name="p_body"]').value = '';
		
	document.querySelector('#write_plan').style.display = 'none';
	
}

function setSelectDateOptions(year, month, select_name) {
	console.log("setSelectDateOptions()");
	
	// set data
	let last = new Date(year, month, 0);
	
	// generate UI
	// let selectElement = document.querySelector('select[name="' + select_name + '"]');
	let selectElement = document.querySelector(`select[name='${select_name}']`);
	
	// add new options
	for (let i = 1; i <= last.getDate(); i++) {
		let option = document.createElement('option');
		option.value = i;
		option.textContent = i;
		selectElement.appendChild(option);
	}
	
}

function setMonthBySelectChanged() {
	console.log("setMonthBySelectChanged()");
	
	let temp_year = document.querySelector("select[name='p_year']").value;
	let temp_month = document.querySelector("select[name='p_month']").value - 1;
	
	let selectedCalender = new Date(temp_year, temp_month, 1);
	setCurrentCalender(
		selectedCalender.getFullYear(),
		selectedCalender.getMonth(),
		selectedCalender.getDate(),
		selectedCalender.getDay()
	);
	
	removeCalenderTr();
	addCalenderTr();
	fetchGetCurrentMonthPlans();			// 일정 가져오기
	
}


function setNextMonth() {
	console.log("setNextMonth()");
	
	let yearSelect = document.querySelector("select[name='p_year']");
	let monthSelect = document.querySelector("select[name='p_month']");
	
	if (yearSelect.value == 2030 && monthSelect.value == 12) {
		alert("2030년 12월 이후는 서비스할 수 없습니다.");
		return false;
	}
	
	let temp_year = current_year;
	let temp_month = current_month + 1;
	
	if (temp_month >= 12) {
		temp_year += 1;
		temp_month = 0;
	}
	
	let preCalender = new Date(temp_year, temp_month, 1);
	setCurrentCalender(
		preCalender.getFullYear(),
		preCalender.getMonth(),
		preCalender.getDate(),
		preCalender.getDay()
	);
	
	setCurrentYearAndMonthSelectUI();		// <select> UI 업데이트
	removeCalenderTr();						// <tr> 제거
	addCalenderTr();						// <tr> 생성
	fetchGetCurrentMonthPlans();			// 일정 가져오기
	
}

function setPreMonth() {
	console.log("setPreMonth()");
	
	let yearSelect = document.querySelector("select[name='p_year']");
	let monthSelect = document.querySelector("select[name='p_month']");
	
	if (yearSelect.value == 2025 && monthSelect.value == 1) {
		alert("2015년 1월 이전은 서비스할 수 없습니다.");
		return false;
	}
	
	let temp_year = current_year;
	let temp_month = current_month - 1;
	
	if (temp_month <= -1) {
		temp_year -= 1;
		temp_month = 11;
	}
	
	let preCalender = new Date(temp_year, temp_month, 1);
	setCurrentCalender(
		preCalender.getFullYear(),
		preCalender.getMonth(),
		preCalender.getDate(),
		preCalender.getDay()
	);
	
	setCurrentYearAndMonthSelectUI();		// <select> UI 업데이트
	removeCalenderTr();						// <tr> 제거
	addCalenderTr();						// <tr> 생성
	fetchGetCurrentMonthPlans();			// 일정 가져오기
		
}

// <tr> 제거
function removeCalenderTr() {
	console.log("removeCalenderTr()");
	
	let tbody = document.querySelector("#table_calender tbody");
	tbody.innerHTML = '';
	
}