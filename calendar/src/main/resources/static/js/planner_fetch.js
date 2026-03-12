// ---------------- fetch start -------------------------
// 일정 등록
async function fetchWritePlan(year, month, date, title, body) {
	console.log("fetchWritePlan()");

	let reqFormData = new FormData();
	reqFormData.append("year", year);
	reqFormData.append("month", month);
	reqFormData.append("date", date);
	reqFormData.append("title", title);
	reqFormData.append("body", body);

	try {
		let response = await fetch('/planner/plan', {
			method: 'POST',
			body: reqFormData
		});

		if (!response.ok) {
			throw new Error('Network response was not ok');
		}

		console.log('fetchWritePlan() COMMUNICATION SUCCESS!!');
		let data = await response.json();
		if (!data) {
			alert('일정 등록에 문제가 발생했어요.');

		} else {
			alert('일정 등록이 정상처리 됐어요.');

		}

	} catch (error) {
		console.log('fetchWritePlan() COMMUNICATION ERROR!!', error);
		alert('일정 등록에 문제가 발생했어요.');

	} finally {
		console.log('fetchWritePlan() COMMUNICATION COMPLETE!!');

		hideWritePlanView();

	}

}

// 일정들 조회
async function fetchGetCurrentMonthPlans() {
	console.log('fetchGetCurrentMonthPlans()');

	let reqData = {
		year: current_year,
		month: current_month + 1
	}

	let queryString = new URLSearchParams(reqData).toString();  // /planner/plans?year=2026&month=3

	try {
		let response = await fetch(`/planner/plans?${queryString}`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json; charset=utf-8'
			}

		});

		if (!response.ok) {
			throw new Error('Network response was not ok');
		}

		console.log('fetchWritePlan() COMMUNICATION SUCCESS!!');
		let data = await response.json();

		let plans = data.plans;
		plans.forEach(dto => {
            let appendTag = `<li><a class="title" href="#none" data-no="${dto.no}">${dto.title}</a></li>`;
            let targetElement = document.querySelector(`#date_${dto.date} ul.plan`);
            if (targetElement) {
                // insertAdjacentHTML('beforeend', ...)은 요소의 마지막 자식으로 새로운 HTML을 삽입하는 기능입니다.
                targetElement.insertAdjacentHTML('beforeend', appendTag);
            }

        });


	} catch(error) {
		console.log('fetchWritePlan() COMMUNICATION FAIL!!', error);

	} finally {
		console.log('fetchWritePlan() COMMUNICATION COMPLETE!!');

	}

}
