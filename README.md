# Compose Multiplatform UI Todo Application
Todoを記録するためのアプリケーション

## 目的
Kotlin Multiplatform の Room と Compose Multiplatform の Adaptive Scaffold を実装するため

## TODO
- テストを書く
- 右クリックと長押しで消せるようにする
- 詳細画面で完了と取り消しを行えるようにする
- 詳細な構成をドキュメントにまとめる
- ライセンス表示を行えるようにする

## ネームスペース以下のロジック部分
* `/data` データレイヤ
  - `/database` データベース関連
  - `/repository` アクセスのためのリポジトリ
* `/domain` ドメインレイヤー（ユースケース）
* `/ui` UIレイヤー
  - `/entities` ViewModelの値保持用のエンティティ
  - `App.kt` メインアプリファイル（Common Composeの一番上）
  - `HomeScreen.kt` ホームスクリーン（マルチプラットフォーム対応）
  - `HomeScreen_.kt` ホームスクリーンの内容
  - `/viewmodel` ViewModelの中身 ViewModelはスマホでの1スクリーン1個

## サードパーティモジュールについて
/thirdparty 以下に配置している
AndroidX Paging Composeのライブラリが当該プラットフォームに対応していなかったため、https://github.com/cashapp/multiplatform-paging.git を参考に実装しなおしたもの。  
multiplatform-pagingを使用しなかった理由は、RoomデータベースのPagingライブラリの移植が困難だったため

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…