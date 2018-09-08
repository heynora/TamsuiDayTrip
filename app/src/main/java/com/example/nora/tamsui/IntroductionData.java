package com.example.nora.tamsui;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 2017/12/24.
 */

public class IntroductionData {
    ArrayList<String> introduction = new ArrayList<>();

    String title,content,address;

    public void check(String name){
        //1
        introduction.add("真理大學");
        introduction.add("台北淡水校區位於台北縣淡水鎮真理街，毗鄰紅毛城和淡水高爾夫球場，遠眺觀音山，俯瞰淡水河出海口，風光極為秀麗。校園內花木扶疏，讀書環境精緻幽雅，各式師生所需之教學、運動、休閒等設施都相當齊全。");
        introduction.add("新北市淡水區真理街32號");
        //2
        introduction.add("淡江中學");
        introduction.add("是藝人周杰倫的母校，他還在此拍攝了電影《不能說的秘密》，學校因此出名。馬偕在滬尾設立一所神學院「理學堂大書院」，為「真理大學」的前身。");
        introduction.add("新北市淡水區真理街26號。");
        //3
        introduction.add("英國領事館");
        introduction.add("聖多明哥城」，做為台北盆地貿易和傳教的據點。 荷人重新建造一座更為堅固的「聖安東尼奧城」，俗稱此城為「紅毛城」。");
        introduction.add("淡水鎮中正路28巷1號");
        //4
        introduction.add("淡水海關碼頭園區");
        introduction.add("淡水海關設施包含：海關關署區、長官官邸區、碼頭區等三大區。\n"+ "碼頭區：全長約為150公尺，有「繫船石」15根以及「登船口」，可供船舶停靠上、下貨之用。");
        introduction.add("新北市淡水區中正路259號");
        //5
        introduction.add("鄞山寺");
        introduction.add("奉祀汀州客家人的保護神「定光古佛」。這裡不僅是北台灣汀州客家人的信仰中心，同時也充作會館。本廟較特別之處是供奉的「定光古佛」為少見的「軟身神像」。 是指神像具有關結、四肢可活動。");
        introduction.add("新北市淡水區鄧公路15號");
        //6
        introduction.add("淡水龍山寺");
        introduction.add("這間「龍山寺」隱身在菜市場內，不太好找，因此少被遊客注意。也是間古剎。本廟主祀觀音菩薩，為三級古蹟。");
        introduction.add("新北市淡水區中山路95巷22號");
        //7
        introduction.add("清水祖師廟");
        introduction.add("主祀閩南安溪高僧清水祖師，又稱「淡水祖師廟」，是大台北地區三大祖師廟之一，另兩座分別為「艋舺祖師廟」和「三峽祖師廟」。");
        introduction.add("新北市淡水區清水街87號");
        //8
        introduction.add("福佑宮");
        introduction.add("緊鄰碼頭，後方是個小山坡，在風水上是所謂「前有照、後有靠」的風水寶地。中法戰爭時傳說在戰況激烈時，觀世音菩薩、媽祖、清水祖師爺等紛紛顯靈助陣，終於擊退法軍。");
        introduction.add("新北市淡水區中正路200號");
        //9
        introduction.add("多田榮吉故居");
        introduction.add("在淡水老街尾，多田榮吉在日治時期擔任淡水街長，這間日式小屋是他的私人宅邸，是淡水第一座被指定為古蹟的日式住宅（市定古蹟）。\n" +
                "\n" +
                "※ 週一至週五上午9點半到下午5點，週末延長至下午6點（無休）\n" +
                "※ 進入室內參觀須脫鞋著襪，更換室內拖鞋。\n");
        introduction.add("淡水馬偕街19號");
        //10
        introduction.add("馬階醫院原址與淡水教會");
        introduction.add("是馬階博士於1879年成立的私立基督教醫院，是台灣第一家西式醫院， 也曾是他的住所。目前為市定古蹟。西方歷史學者以：「寧願燒盡，不願朽壞」來讚賞馬偕的一生。");
        introduction.add("新北市淡水區馬偕街6號");
        //11
        introduction.add("淡水老街");
        introduction.add("緊鄰海岸便有眾多海鮮餐廳林立，其中以海風餐廳最受歡迎。淡水老街小吃方面，淡水魚丸、魚酥、鐵蛋、阿給等最膾炙人口。");
        introduction.add("新北市淡水區中正路、重建街、清水街");
        //12
        introduction.add("淡水殼牌倉庫");
        introduction.add("位於捷運淡水站旁的鼻仔頭，由著名的茶葉外銷洋行「嘉士洋行」所承租，用以經營茶葉貿易。於1897年由殼牌公司買下，並增建四座大型磚造倉庫。並舖設可接通淡水線鐵路的鐵道。也由於煤油臭氣瀰漫，又讓淡水人稱之為「臭油棧」，直到經美軍轟炸使得油槽起火，從此也走向沒落，並轉做備用倉庫。");
        introduction.add("新北市淡水區鼻頭街22號");
        //13
        introduction.add("滬尾砲台");
        introduction.add("中法戰爭曾在這裡開戰，戰後台灣巡撫劉銘傳就在淡水興建新砲台加強防衛，就是今日的「滬尾砲台」，列為國定古蹟。門額上留存劉銘傳親筆題的「北門鎖鑰」石碑。該砲台停用多年，但為長期屬軍事要塞，因此結構大致保存完整。");
        introduction.add("新北市淡水區中正路一段6巷34號");
        //14
        introduction.add("英商嘉士洋行");
        introduction.add("清代的淡水開放洋人通商，有洋人租界，這裡就是「英商嘉士洋行」的倉庫。");
        introduction.add("新北市淡水區鼻頭街22號");
        //15
        introduction.add("淡水情人橋");
        introduction.add("漁人碼頭最著名的景點。330多公尺的木棧道、堤岸咖啡和超大的港區公園，構成一個環狀動線，讓人們可以完整流暢的體驗漁港風情。看著夕陽從水平面上緩緩落下，令人流連忘返。");
        introduction.add("新北市淡水區沙崙里第2漁港");
        //16
        introduction.add("淡水港");
        introduction.add("淡水港又稱滬尾海關碼頭，是淡水河的河口港，曾經為臺灣三大商港之一，因為地理位置重要，所以在此設通商貿易港。日本佔領台灣後大力建設基隆港，兼以台北基隆間鐵路之便，基隆港終而取代了淡水港昔日的地位。");
        introduction.add("251新北市淡水區中正路259號");
        //17
        introduction.add("小白宮");
        introduction.add("建築為西班牙白堊迴廊式建築，表現第二個家的生活情趣，將維多利亞時代的紅磚鄉村建築與印度熱帶建築拱廊結合創造出的一種新樣式。視覺上具有協調柔和與美感。可見設計者的巧思與才能。");
        introduction.add("新北市淡水區真理街15號");
        //18
        introduction.add("一滴水紀念館");
        introduction.add("日本文學大師水上勉之父水上覺治所建造，黑色屋瓦、木頭樑柱所構築的古民家，具有充滿歷史感的古樸風情，榻榻米和傳統地爐也讓人仿佛置身日本住宅中。");
        introduction.add("新北市淡水區中正路一段6巷(和平公園內)");
        //19
        introduction.add("世界巧克力夢工廠");
        introduction.add("坐落於淡水漁人碼頭，以「巧遇幸福、夢想成真」為概念，擁有五大主題展區。世界頂尖大師巧克力工藝絕技齊聚一堂，一趟超乎想像的巧克力甜蜜之旅，帶領遊客暢遊歡樂、浪漫、奇幻、夢想的巧克力園地，讓每一個角落都充滿驚奇。");
        introduction.add("新北市淡水區觀海路91號");
        //20
        introduction.add("淡水莊園");
        introduction.add("位於洲子灣海岸，浪漫夕陽下的沙灘海景，成為新人們首選的外拍場地。除此之外，莊園永遠掌握即時性的流行趨勢，不定期的創新及優化場景，讓新人們留下與眾不同的絕美作品，享受獨一無二的幸福氛圍。");
        introduction.add("新北市淡水區興仁里前洲子路20號");
        //21
        introduction.add("紫藤咖啡館");
        introduction.add("前身為淡水三空泉之咕咕鐘歐式花園，在淡水區以花園西餐廳聞名。\n" +
                        "每年預計是3/15至4月底開放，其餘時間不對外開放參觀， 紫藤難以照顧，其餘時需精心照顧，園主堅持每年限制開放時間，才能讓遊客欣賞到最漂亮的紫藤花。\n");
        introduction.add("新北市淡水區屯山里石頭厝2-1號");

        int index =introduction.indexOf(name);
        Log.e("DEBUG"," "+index);

        title = introduction.get(index);
        content = introduction.get(index+1);
        address = introduction.get(index+2);
    }
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getAddress(){return address;}
}
