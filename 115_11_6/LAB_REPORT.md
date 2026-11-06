# 第五週學生實作：任務日誌類別化

姓名：陳家柔　學號：A110223043　實際上課日期：11/6　實際 JDK：java 24.0.1、javac 24.0.1

A–H 必做。先預測再執行；教師示範觀察與本人實測分開。【待填】標記處請本人補齊。

## A｜取回與診斷（10 分鐘）

1. 取回第五週教材；在 115_1_Java/115_11_6 放 examples 六檔與 starter 兩檔（MissionStep、MissionLog），不放 teacher 同名類別。
2. 編譯指令：`javac -encoding UTF-8 -d out *.java`，成功無錯誤訊息。

診斷：第四週用三條清單（commandLog、energyLog、resultLog）保存同一步的三個欄位。若第 2 步漏加 energyLog，第 3 步的能源會被存到索引 1，之後同一索引讀到的三欄不屬於同一步，回放錯位，讀最後一筆還會越界。改成每步一個 MissionStep 物件，三欄綁在一起就不會對不齊。

## B｜類別、物件與建構子（25 分鐘）

執行 CreateStepDemo（搭配 StepCard）：

| 項目 | 預測 | 本人實際 |
|---|---|---|
| first | 1/14 | first=1/14 |
| second | 2/12 | second=2/12 |
| same object | false | same object=false |

- `StepCard first = new StepCard(1, 14);`：StepCard 是類別（型態），`new StepCard(1,14)` 建立物件，first 是參考變數。
- 建構子以 `this.energy = energy;` 把參數存進目前物件的欄位；若寫 `energy = energy` 只是參數指定給自己，欄位仍為 0。
- 兩次 new 會建立兩個不同物件，所以 same object=false。

故意製造編譯錯誤：在 CreateStepDemo 第 8 行加入 `StepCard bad = new StepCard();`，編譯結果：

```
CreateStepDemo.java:8: error: constructor StepCard in class StepCard cannot be applied to given types;
        StepCard bad = new StepCard();
                       ^
  required: int,int
  found:    no arguments
  reason: actual and formal argument lists differ in length
1 error
```

原因：StepCard 已自行宣告有參數建構子，編譯器不會再自動補無參數版本。觀察：編譯失敗後執行仍印出舊結果，是 out 內上一次成功編譯的 class，不能當成這次通過的證據。已刪除該行，重新編譯無錯誤、輸出恢復。

## C｜封裝（15 分鐘）

執行 BatteryDemo（搭配 RoverBattery）：

```
explore=false, energy=5
charge=true, energy=10
explore=true, energy=4
cap=true, energy=20
full=false, energy=20
```

能源欄位為 private，外部只能透過 explore／charge 改變、getEnergy 查詢；能源不足時拒絕且不扣電，充電封頂 20，已滿時拒絕。

## D｜參考與快照（20 分鐘）

執行 ReferenceDemo：

```
after alias=14/20
same reference=true
after spend=8
inside replacement=1
after replace=8
```

- `alias = first` 讓兩個變數指向同一電池，alias.explore() 後 first 也看到 14；other 是另一次 new，仍為 20。
- 方法取得的是參考值的副本：spend 操作同一電池，回來後是 8；replaceLocal 在方法內改指向新電池（1），回到 main 後 first 仍指向能源 8 的舊電池。

執行 SnapshotDemo：

```
command=1, energy=14
command=3, energy=19
current=19
separate records=true
```

每步行動後 new StepCard 保存當下的 int 能源，之後充電不會把第一筆 14 改成 19。

## E｜實作 MissionStep 與 MissionLog（30 分鐘）

- TODO 1：建構子以 this 保存 command、energy、result。
- TODO 2：三個 getter 回傳本物件欄位；不提供 setter。
- TODO 3：`isSuccessful()` 用 `"OK".equals(result)` 比較內容。
- TODO 4：switch 之後 `history.add(new MissionStep(command, energy, result));`，拒絕、無效、返回也新增。
- TODO 5：printReplay 以索引 for 迴圈 `history.get(i)` 取物件，再用 getter 印出。
- TODO 6：printSummary 走訪 history，`isSuccessful()` 計 ok，result 為 RETURN 計 returned。

兩筆獨立查詢測試（第 27 頁資料，測後已移除）：

| 物件 | 預期 command/energy/result/isSuccessful | 本人實際 |
|---|---|---|
| first `new MissionStep(1, 14, new String("OK"))` | 1/14/OK/true | 1/14/OK/true |
| second `new MissionStep(2, 12, "NOT_ENOUGH_ENERGY")` | 2/12/NOT_ENOUGH_ENERGY/false | 2/12/NOT_ENOUGH_ENERGY/false |

建立 second 沒有覆蓋 first。

核心路線（初始 20/0，每次改 main 的 script 後重新編譯）：

| script | 預期終態 | 本人實際終態 | 回放、counts、摘要、最低能源、battery 相符？ |
|---|---|---|---|
| {1,1,2} | 6/5/3 SUCCESS | 6/5/3 SUCCESS | 是 |
| {3,9,0} | 20/0/0 RETURNED | 20/0/0 RETURNED | 是 |

完整輸出：

```
--- {1,1,2}
=== replay ===
step 1: command=1, result=OK, energy=14
step 2: command=1, result=OK, energy=8
step 3: command=2, result=OK, energy=6
counts[0..3]=[0, 2, 1, 0]
steps=3, ok=3, rejected=0, ignored=0
lowest energy=6
final energy=6, samples=5, actionCount=3 -> SUCCESS
battery=30.0

--- {3,9,0}
=== replay ===
step 1: command=3, result=ALREADY_FULL, energy=20
step 2: command=9, result=UNKNOWN_COMMAND, energy=20
step 3: command=0, result=RETURN, energy=20
counts[0..3]=[1, 0, 0, 1]
steps=3, ok=0, rejected=2, ignored=0
lowest energy=20
final energy=20, samples=0, actionCount=0 -> RETURNED
battery=100.0
```

輸出與第四週相同，日誌改用 `ArrayList<MissionStep>` 後行為不變。繳交前已恢復 {1,1,2}。

## F｜需求設計與反例（30 分鐘）

### F1 SampleCase 設計卡

責任：管理一個樣本運送盒的目前存量，只允許合法的存入與卸空。

| 設計卡 | 本人決定 |
|---|---|
| 責任、欄位、可見性 | `private int capacity`、`private int count`；剩餘空間用 capacity − count 計算，不另存以免不一致；private 讓外部不能直接改存量 |
| 建構子 | `SampleCase(int capacity)`：小於 1 設為 1、大於 8 設為 8，count 為 0 |
| 方法契約 | `boolean store(int amount)`：amount ≤ 0 或超過剩餘空間回傳 false、存量不變，否則加入並回傳 true；`int unloadAll()`：回傳目前存量並歸 0，空盒回傳 0；`int getCount()`；`int getRemaining()`；不提供 setter |
| 測試 | 見下表 |

容量 3 的預期：

| 呼叫 | 回傳 | 存量 | 剩餘 |
|---|---|---|---|
| store(2) | true | 2 | 1 |
| store(2) | false（放不下，整批拒絕） | 2 | 1 |
| store(1) | true | 3 | 0 |
| unloadAll() | 3 | 0 | 3 |
| unloadAll()（空盒） | 0 | 0 | 3 |

其他：store(0)、store(-1) 回傳 false；new SampleCase(0) 容量 1；new SampleCase(10) 容量 8。（紙上推論，未實作）

### F2 審查模擬 AI 程式與改寫 Prompt

| # | 偏差 | 呼叫例子 | 需求應為 |
|---|---|---|---|
| 1 | 欄位 public，外部可任意改寫 | `c.count = 100;` | 不能任意改存量 |
| 2 | 建構子未修正容量 | new SampleCase(0) 容量 0；new SampleCase(10) 容量 10 | 應為 1 與 8 |
| 3 | store 不檢查容量 | 容量 3，store(2) 兩次後 count=4 | 第二次拒絕，保留 2 |
| 4 | store 不拒絕非正數 | store(-1) 後 count=-1 | 回傳 false，存量不變 |
| 5 | store 回傳 void | store(5) 無法得知成功與否 | 應回傳 boolean |
| 6 | 缺少卸空與查剩餘空間 | 無 unloadAll()、getRemaining() | 需求要求提供 |

改寫後 Prompt：

> 請設計 SampleCase 類別，責任是管理一個樣本運送盒的目前存量。建構規則：SampleCase(int capacity)，容量小於 1 時設為 1、大於 8 時設為 8，初始存量為 0；欄位一律 private，不提供 setter。公開方法：boolean store(int amount)，份數須為正且不超過剩餘空間，否則整批拒絕、回傳 false、存量不變；int unloadAll() 一次卸空並回傳卸下份數，空盒回傳 0；int getCount() 回傳存量；int getRemaining() 回傳容量減存量。請用以下測試檢查：容量 3 時 store(2) 為 true、存量 2；再 store(2) 為 false、存量仍 2；store(1) 為 true、存量 3；unloadAll() 回傳 3、存量 0；store(-1) 為 false；new SampleCase(0) 容量為 1，new SampleCase(10) 容量為 8。限定一般 class，不用 record 或繼承。

### F3 模擬 AI 除錯三選一：選項二（用 == 比較結果）

在 repo 外副本（Desktop/week05_bug）操作，正式 src 保持正確版。

| 證據 | 本人紀錄 |
|---|---|
| 模擬建議 | 「固定代碼，用 == 比較就好」：`return result == "OK";` |
| 錯誤版結果 | `new MissionStep(1, 14, new String("OK")).isSuccessful()` 印出 false；但 {1,1,2} 仍為 6/5/3 SUCCESS、ok=3 |
| 原因 | == 比較參考而非內容；new String("OK") 是另一個物件所以為 false。任務流程用字串常值 "OK"，常值共用同一物件，== 碰巧成立，只測正常路線抓不到錯 |
| 處理 | 拒絕此建議，恢復 `"OK".equals(result)` |
| 修正後 | isSuccessful=true；{1,1,2} 為 6/5/3 SUCCESS；{3,9,0} 為 20/0/0 RETURNED |

## G｜檢核（5 分鐘）

1. 類別、物件、參考變數的差別：類別是藍圖與型態；new 建立物件；參考變數指向物件，只宣告不 new 不會有物件。
2. 為何 energy 不能是 static：static 屬於類別，所有 MissionStep 共用一份，後建立的記錄會覆蓋前一筆。
3. 為何 MissionStep 不提供 setter：它保存已發生的歷史，建立後只讀。
4. 為何 TODO 4 要放在 switch 之後：此時 result 才有值，energy 才是行動後的能源；放在行動前會記錯時間點。
5. 類別責任：MissionStep 保存一筆已發生的任務記錄；MissionLog 推進腳本、保存歷史、產生報表；RoverBattery 管理能源及受控變更。

## H｜保存、推送與取回（15 分鐘）

- main 的 script 已恢復 {1,1,2}，未留臨時檢查；重新編譯執行為 6/5/3 SUCCESS、battery=30.0。
- 錯誤副本留在 repo 外；out 不提交。
- GitHub repo／作業目錄：https://github.com/meat0222/115_1_Java ／ 115_11_6；程式 commit：9c6e9fc（Week05 MissionStep 類別化任務日誌與實作報告），已推送，GitHub main 與本機一致。
- 新副本 JDK／編譯／預設輸出：另一位置 clone 後，JDK 24.0.1；含 examples 六檔與 starter 兩檔，`javac -encoding UTF-8 -d out *.java` 編譯成功；MissionLog 預設 {1,1,2} 回放能源 14、8、6，final energy=6, samples=5, actionCount=3 -> SUCCESS，battery=30.0（clone 驗證由 AI 協助執行）。
