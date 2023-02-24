# 購物車練習
## 介紹
這是一個基於Java開發的專案，算是一個實作的練習項目，練習使用Springboot框架進行開發。  
- 內部功能包含:
	- 使用者的註冊、登入以及登出。
	- 產品的呈現、加入購物車以及購買
	- 購物車連結產品進行呈現、修改以及刪除
	- 購物車資料轉化為訂單
	- 訂單的查看以及刪除
	- 使用者申請成為賣家，添加商品以及刪除商品

資料庫的部分則是使用MySQL，並利用Mybatis操作。  
前端的部分，靜態的RWD頁面主要是使用HTML/CSS完成的。  
前端向後端傳遞和接收的資料則是選擇利用Vue.js和axios來完成並展示，可以順便了解不同於只使用Ajax研發的差異。  
## 功能介紹
### 前言
剛開始使用頁面時除了登入頁面、註冊頁面以及產品頁面可以查看外其餘的頁面都將被Interceptor(攔截器)擋掉。  
包含商品頁面除查看以外的操作，都會被擋掉，這個專案大多數的訊息顯示是使用sweetalert2完成的，如下圖:

### 註冊
讓我們先從註冊開始，這邊的設計如下，註冊需要填寫帳號、信箱、密碼以及確認密碼等訊息。  
在這邊會有幾個驗證需求:
- 帳號若有重複使用將會被檢測並攔截
- mail格式若有問題或是為空，將會被攔截
- 密碼以及確認密碼的區塊若是為空，或是兩者不同將會被攔截  

大致的操作狀況如下圖:  
![This is an image](https://github.com/YHYen/Shopping-Cart/blob/master/src/displayed%20pictures/register.gif)

### 登入
接著是登入，在註冊完成後，將會直接跳轉過來。輸入已註冊好的帳號密碼即可，這邊同樣有帳號密碼的驗證。  

大致操作如下圖:  
![This is an image](https://github.com/YHYen/Shopping-Cart/blob/master/src/displayed%20pictures/login.gif)

### 加入購物車
在登入之後，會自動跳轉產品頁面。在這邊，剛才未登入時不能做的操作已經可以使用了。主要的操作包含:
- 添加購物車
- 直接購買(將添加訂單)    

先來說明加入購物車的操作，來到購物車頁面，將可以看到剛剛添加的商品以及該商品的圖片。  
在這裡，可以對購物車內的商品進行操作，這些操作會改變頁面上顯示的金額，大致有以下功能:
- 商品數量修改
- 商品刪除
- 購物車清空
- 將購物車內容變為訂單(這個操作將會清空購物車)

大致操作如下圖:  
![This is an image](https://github.com/YHYen/Shopping-Cart/blob/master/src/displayed%20pictures/shoppingCart.gif)

### 訂單
在訂單頁面，將會把所有已購買的商品添加到訂單中(包含在產品頁面直接購買的產品)，不同時間添加的商品，其訂單將會有所區分。  
在這裡，同樣可以對訂單進行操作，大致有以下功能:
- 訂單內商品的查看
- 訂單的刪除

大致操作如下圖:   
![This is an image](https://github.com/YHYen/Shopping-Cart/blob/master/src/displayed%20pictures/order.gif)

### 成為賣家
完成訂單的操作後，這個專案的第二種使用者，我設定為賣家，相對於買家，成為賣家通常需要一些資料進行申請，例如: 身分證照片。  
所以在這個頁面，會需要使用者上傳圖片以供驗證。  
但專案還沒有加入管理者的設定，所以這裡的驗證是默認通過的。  

成為賣家後，上方欄將會增加商店頁面的連結，包含賣家的商店頁面以及新增商品兩個選項。  
在新增商品頁面可以看到表單，填入商品相應資料，如:名稱、價格數量以及要顯示的圖片等資訊，便可以添加商品。  
商品添加後，將會自動導向產品頁面，在裡面可以看到剛剛上傳的商品。  
而此時在商店頁面，可以看到由自己添加的商品資訊，在這邊可以對自己的商品進行操作，操作如下。  
- 修改商品
	- 按下修改商品後，將會跳轉頁面，在近似於添加商品的頁面中對商品資訊進行修改。
- 刪除商品  

大致流程如下圖:   
![This is an image](https://github.com/YHYen/Shopping-Cart/blob/master/src/displayed%20pictures/seller.gif)

### 新增商品頁面
成為賣家後，上方欄將會增加商店頁面的連結，包含賣家的商店頁面以及新增商品兩個選項。  
在新增商品頁面可以看到表單，填入商品相應資料，如:名稱、價格數量以及要顯示的圖片等資訊，便可以添加商品。  
商品添加後，將會自動導向產品頁面，在裡面可以看到剛剛上傳的商品。  

### 登出
在所有操作結束後，右上角使用者icon的下拉式選單中有登出按鈕，按下後將會回到商品頁面，並且消除使用者session，回到登入前狀態。

## 資料庫設計
在這邊將展現這個專案的各個資料表以及其關連，關聯圖如下:  
![This is an image](https://github.com/YHYen/Shopping-Cart/blob/master/src/displayed%20pictures/%E8%B3%87%E6%96%99%E8%A1%A8%E8%A8%AD%E8%A8%88.png)

## 資料夾說明
- 後端的程式碼都放在 src\main\java\com\idv\yen的目錄下  
	- config	: 中放的是Session 和 Interceptor 相關的設定  
	- controller: 以資料表分類，其中放的是接收前端請求後，調用後端service的控制  
	- domain	: entity層，放的是各個物件的基本設定
	- mapper	: dao層，用於與資料庫的資料進行操作
	- service	: 介面，主要是定義業務邏輯
	- service/impl: 用來實作各個service介面的業務邏輯
	- service/Utils: 這裡是存放serviceImpl中操作時會用到的工具，如:圖片的上傳下載
- 前端的程式碼放在 src\main\resources\static 的目錄下
	- css		: 前段介面設計樣式的設定
	- images	: 放的是頁面中操作會使用到的圖片，以及賣家申請和添加商品上傳的圖片
- 在程式碼中有些映射資料較多的地方有使用到ResultMap，相關的設定放在 src\main\resources\com\idv\yen\mapper 中

## 使用技術
- 後端
	- springboot
	- Mybatis
	- MySQL
- 前端
	- HTML
	- CSS
	- Javascript
	- Vue.js
	- axios
