package com.hooooong.contact;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hooooong.contact.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Content Resolver 사용하기
 *
 *  1. Content Resolver 를 불러오기
 *  2. Data URI 정의 <- 일종의 DB의 Table 이름
 *  3. Data URI 에서 가져올 칼럼명 정의
 *      - 조건절을 정의할 수 있다.
 *  4. Content Resolver(CR) 로 쿼리한 데이터를 Cursor에 담는다.
 *  5. Cursor 에 담김 데이터를 반복문으로 돌면서 처리한다.
 *
 *      - 권한 설정
 *      Manifest.permission.READ_CONTACTS
 *      Manifest.permission.READ_EXTERNAL_STORAGE
 *      Manifest.permission.CALL_PHONE
 *
 */
public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;

    public MainActivity() {
        super(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE});
    }

    @Override
    public void init(){
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        MainAdapter adapter = new MainAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.addData(load());
    }

    private List<Contact> load(){
        List<Contact> contactList = new ArrayList<>();
        // 1. Content Resolver 불러오기
        ContentResolver resolver = getContentResolver();
        // 2. Data URI 정의
        // URI : 주소의 프로토콜을 포함한 형태
        // ContactsContract.CommonDataKinds.Phone.CONTENT_URI : 전화번호 URI
        //  ContactsContract.Contacts.CONTENT_URI : 주소록 URI
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

                index = cursor.getColumnIndex(projection[3]);

                if(cursor.getString(index) != null){
                    String path = cursor.getString(index);
                    contact.setThumbnail(path);
                }
                contactList.add(contact);
            }
        }

        return contactList;
    }
}


