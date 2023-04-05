<template>
  <h3>vue ch02</h3>
  <h4>1&#41;v-text</h4>
  <p v-text="tit1"></p>
  <p>{{ tit2 }}</p>
  <hr />
  <h4>2&#41;v-show</h4>
  <p v-show="result1">show directive1</p>
  <p v-show="result2">show directive2</p>
  <hr />
  <h4>3&#41;v-if</h4>
  <p v-if="result1">if directive1</p>
  <p v-if="result2">if directive2</p>
  <p v-else>if directive3</p>

  <p v-if="score >= 90">A입니다</p>
  <p v-else-if="score >= 80">B입니다</p>
  <p v-else-if="score >= 70">C입니다</p>
  <p v-else-if="score >= 60">D입니다</p>

  <p v-if="tit3">{{ tit3 }}</p>
  <p v-else>tit3 empty...</p>
  <hr />
  <h4>4&#41;v-for</h4>
  <ul>
    <li v-for="i in 3">i : {{ i }}</li>
  </ul>
  <ul>
    <li v-for="city in cities">city : {{ city }}</li>
  </ul>
  <table border="1">
    <tr>
      <th>아이디</th>
      <th>이름</th>
      <th>나이</th>
    </tr>
    <tr v-for="person in persons">
      <td>{{ person.uid }}</td>
      <td>{{ person.name }}</td>
      <td>{{ person.age }}</td>
    </tr>
  </table>
  <hr />
  <h4>5&#41;v-on</h4>
  <h4>click 예제</h4>
  <button v-on:click="handler1">btn1</button>
  <button v-on:click="handler2(10)">btn2</button>
  <button @click="handler3">btn3</button>

  <h4>count 예제</h4>
  <p>{{ count }}</p>
  <button @click="countHandler">카운터</button>

  <h4>change 예제</h4>
  <select @change="changeHandler">
    <option value="서울">서울</option>
    <option value="대전">대전</option>
    <option value="대구">대구</option>
    <option value="부산">부산</option>
    <option value="광주">광주</option>
  </select>

  <h4>submit 예제</h4>
  <form @submit.prevent="submitHandler">
    <table border="1">
      <tr>
        <td>아이디</td>
        <td><input type="text" name="uid" /></td>
      </tr>
      <tr>
        <td>이름</td>
        <td><input type="text" name="name" /></td>
      </tr>
      <tr>
        <td>나이</td>
        <td><input type="text" name="age" /></td>
      </tr>
      <tr>
        <td>주소</td>
        <td>
          <select name="addr" id="">
            <option value="서울">서울</option>
            <option value="대전">대전</option>
            <option value="대구">대구</option>
            <option value="부산">부산</option>
            <option value="광주">광주</option>
          </select>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="right">
          <input type="submit" value="전송" />
        </td>
      </tr>
    </table>
  </form>
  <hr />

  <h4>6&#41;v-bind</h4>
  <img v-bind:src="path1" alt="" />
  <img v-bind:src="path2" alt="" />

  <a :href="url1">네이버</a>
  <a :href="url2">카카오</a>

  <button @click="oneClick" v-bind:disabled="isActive">한번만 클릭되는 버튼</button>
  <hr />
  <h4>7&#41;v-model</h4>
  <h4>input</h4>
  <p>이름 : {{ name }}</p>
  <input type="text" v-model="name" placeholder="이름 입력" />

  <h4>textarea</h4>
  <p>{{ message }}</p>
  <p>문자수 :{{ message.length }}</p>
  <textarea v-model="message" id="" cols="30" rows="10"></textarea>

  <h4>checkbox</h4>
  <p>
    <input type="checkbox" v-model="isChecked" />
    체크상태 : {{ isChecked }}
  </p>

  <label for="ch1"> <input type="checkbox" value="등산" v-model="hobbies" name="ch1" />등산 </label>
  <label for="ch2"> <input type="checkbox" value="영화" v-model="hobbies" name="ch2" />영화 </label>
  <label for="ch3"> <input type="checkbox" value="게임" v-model="hobbies" name="ch3" />게임 </label>
  <label for="ch4"> <input type="checkbox" value="낚시" v-model="hobbies" name="ch4" />낚시 </label>
  <label for="ch5"> <input type="checkbox" value="독서" v-model="hobbies" name="ch5" />독서 </label>
  <br />
  <p>선택값 : {{ hobbies }}</p>

  <h4>select</h4>
  <select name="" id="" v-model="country">
    <option>한국</option>
    <option>미국</option>
    <option>일본</option>
    <option>중국</option>
    <option>호주</option>
  </select>
  <p>선택 : {{ country }}</p>

  <h4>form</h4>
  <form @submit.prevent="submitHandler2">
    <table border="1">
      <tr>
        <td>아이디</td>
        <td><input type="text" v-model="user.uid" /></td>
      </tr>
      <tr>
        <td>이름</td>
        <td><input type="text" v-model="user.name" /></td>
      </tr>
      <tr>
        <td>나이</td>
        <td><input type="text" v-model="user.age" /></td>
      </tr>
      <tr>
        <td>주소</td>
        <td>
          <select v-model="user.addr" id="">
            <option value="서울">서울</option>
            <option value="대전">대전</option>
            <option value="대구">대구</option>
            <option value="부산">부산</option>
            <option value="광주">광주</option>
          </select>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="right">
          <input type="submit" value="전송" />
        </td>
      </tr>
    </table>
  </form>
</template>

<script>
/*
  날짜 : 2023/04/03
  이름 : 김지홍
  내용 : Ch02.Vue Directive 실습
  
  프로젝트 생성/실행
  - npm init vite-app Ch02
  - cd ch02
  - npm install
  - npm run dev
*/

export default {
  name: 'App',
  data() {
    return {
      tit1: 'Hello',
      tit2: 'Welcome',
      tit3: '',
      result1: true,
      result2: false,
      score: 80,
      cities: ['서울', '대전', '대구', '부산', '광주'],
      persons: [
        { uid: 'a101', name: '김유신', age: 23 },
        { uid: 'a102', name: '김춘추', age: 24 },
        { uid: 'a103', name: '강감찬', age: 25 },
        { uid: 'a104', name: '장보고', age: 31 },
        { uid: 'a105', name: '이순신', age: 42 },
        { uid: 'a106', name: '홍길동', age: 35 },
        { uid: 'a107', name: '이성계', age: 28 },
      ],
      count: 0,
      path1: '/img/flower1.jpg',
      path2: '/img/flower2.jpg',
      url1: 'https://naver.com',
      url2: 'https://kakao.com',
      isActive: false,
      name: '',
      message: '',
      isChecked: false,
      hobbies: [],
      country: '한국',
      user: {
        uid: '',
        name: '',
        age: '',
        addr: '',
      },
    };
  },

  methods: {
    handler1: function () {
      alert('btn click');
    },
    handler2: function (val) {
      alert('btn2 click num : ' + val);
    },
    handler3: function (e) {
      alert('btn2 click num : ' + e);
    },
    countHandler: function () {
      this.count++;
    },
    changeHandler: function (e) {
      alert(e.target.value);
    },
    submitHandler: function (e) {
      let uid = e.target.elements.uid.value;
      let name = e.target.elements.name.value;
      let age = e.target.elements.age.value;
      let addr = e.target.elements.addr.value;

      console.log('uid : ' + uid);
      console.log('name : ' + name);
      console.log('age : ' + age);
      console.log('addr : ' + addr);
    },
    submitHandler2: function () {
      console.log('uid : ' + this.user.uid);
      console.log('name : ' + this.user.name);
      console.log('age : ' + this.user.age);
      console.log('addr : ' + this.user.addr);
    },
    oneClick: function () {
      alert('한번만 클릭할 수 있습니다.');
      this.isActive = true;
    },
  },
};
</script>
