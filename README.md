# financial
Untuk mempermudah proses pengecekan, saya melakukan deploy Repository Spring Boot ini dan PostgreSQL Database ke Heroku (host: https://financial-alami.herokuapp.com/).
Namun dikarenakan paket yang saya gunakan tidak berbayar, **kemungkinan membutuhkan waktu yg cukup lama untuk request pertama (Maksimal pernah sampai 20s, silahkan cancel dan request ulang jika ini terjadi).** 

## Struktur PostgreSQL
Terkait struktur database, dapat dilihat pada postgre-structure.png

## Documentation
Terkait dokumentasi API, terdapat pada file postman.zip

## Istilah yang Digunakan Untuk transaction_type
- **Withdraw** : Penarikan, balance dari member yang bersangkutan akan DITAMBAHKAN sebanyak amount yang diinput
- **Deposit** : Penyetoran, balance dari member yang bersangkutan akan DIKURANGKAN sebanyak amount yang diinput
- **Loan** : Peminjaman, balance dari member yang bersangkutan akan DITAMBAHKAN sebanyak amount yang diinput
- **Paid Off** : Pelunasan hutang, balance dari member yang bersangkutan akan DIKURANGKAN sebanyak amount yang diinput
