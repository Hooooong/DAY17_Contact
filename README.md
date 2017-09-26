Android Programing
----------------------------------------------------
### 2017.09.26 11일차

#### 예제
____________________________________________________

- 주소록 가져오기

- Gallery & Camera 사용하기

- CustomGallery 만들기

#### 공부정리
____________________________________________________

##### __Intent__

- Intent란?

  > 하나의 컴포넌트가 __다른 컴포너트를 실행시킬 수 있는 메커니즘__

  - `Intent` 는 명시적, 암시적 인텐트 두 가지 종류가 있다.

  - 다른 분류로는 `Broadcast Intent`가 있다.

- Explicit Intent(명시적 인텐트)

  - 명시적 인텐트는 호출하고자 하는 컴포넌트를 지정해서 메세지를 전달한다.

  ```java
  // 호출하고자 하는 컴포넌넌트를 지정
  Intent intent = new Intent(this, SubActivity.class);
  startActivity(intent);
  ```

- Implicit Intent(암시적 인텐트)

  - 암시적 인텐트는 정의되어 있는 ACTION들 지정하여 호출한다.

  ```java
  // 전화를 발신하는 인텐트 실행
  Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01011112222"));
  // ACTION_CALL 에 해당하는 Activity 를 실행한다.
  startActivity(intent);
  ```

  - Android 에 내장된 ACTION들 말고도 `Intent Filter`를 이용해서 암시적으로 컴포넌트를 호출할 수 있다.

  - 이 방법은 `Manifest.xml`에 정의해야 한다.

  ```xml
  <activity android:name=".클래스이름">
    <intent-filter>
        <action android:name="action.customaction" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
  </activity>
  ```

  ```java
  // Intent 를 생성하면서 Manifest에 정의된 액션명으로 호출한다.
  Intent intent = new Intent("action.customaction");
  startActivity(intent);
  ```
- 참조 : [Intent]()

##### __Content Provider__

![Provider & Resolver] ()

- Provider & Resolver 란?

  > 위 그림과 같이 Android 는 기본적으로 서로 다른 APP들의 DB에 접근이 제한되어 있다. 이 DB의 접근을 주기 위해서 Provider 와 데이터를 받기 위해 Resolver 를 만들어야 한다.

- Content Resolver 작성 방법

  1. Content Resolver 를 불러오기

  2. Data URI 정의 <- 일종의 DB의 Table 이름

  3. Data URI 에서 가져올 칼럼명 정의

  4. Content Resolver(CR) 로 쿼리를 실행한 데이터를 Cursor에 담는다.

  5. Cursor 에 담김 데이터를 반복문으로 돌면서 처리한다.

- Content Resolver 예제

  - 주소록 가져오기

  ```java
  // Content Resolver 를 통해 주소록 ID, 이름, 전화번호 목록을 가져온다.
  private List<Contact> load(){

      List<Contact> contactList = new ArrayList<>();

      // 1. Content Resolver 불러오기
      ContentResolver resolver = getContentResolver();

      // 2. Data URI 정의
      // URI : 주소의 프로토콜을 포함한 형태
      // ContactsContract.CommonDataKinds.Phone.CONTENT_URI : 전화번호 URI
      // ContactsContract.Contacts.CONTENT_URI : 주소록 URI
      Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

      // 3. 가져올 칼렴명 정의
      String projection[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
              ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
              ContactsContract.CommonDataKinds.Phone.NUMBER,
              ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI};

      // 4. 쿼리 결과 -> Cursor
      Cursor cursor = resolver.query(uri,projection, null, null, null);

      // 5. 커서 반복문 처리
      if(cursor != null){
          while(cursor.moveToNext()){
              Contact contact = new Contact();
              int index =cursor.getColumnIndex(projection[0]);
              contact.setId(cursor.getInt(index));

              index =cursor.getColumnIndex(projection[1]);
              contact.setName(cursor.getString(index));

              index =cursor.getColumnIndex(projection[2]);
              contact.setNumber(cursor.getString(index));
              contactList.add(contact);
          }
      }

      return contactList;
  }
  ```

  - Gallery Image 가져오기

  ```java
  // Content Resolver 를 통해 Image 목록을 가져온다.
  private List<String> load(){
      List<String> list = new ArrayList<>();

      // 1. Content Resolver 불러오기
      ContentResolver resolver = getContentResolver();

      // 2. Data URI 정의
      // MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI :
      Uri uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

      // 3. 가져올 칼렴명 정의
      String projections[] = {
              // 이미지는 Data 칼럼에 존재
              MediaStore.Images.Thumbnails.DATA
      };

      // 4. 쿼리 결과 -> Cursor
      Cursor cursor = resolver.query(uri, projections, null, null, null);

      // 5. 커서 반복문 처리
      if(cursor != null ){
          while (cursor.moveToNext()){
              int index = cursor.getColumnIndex(projections[0]);
              // Uri 를 String 으로 받을 경우 String(Path) 로 받는다.
              String path = cursor.getString(index);
              list.add(path);
          }
      }
      return list;
  }
  ```
