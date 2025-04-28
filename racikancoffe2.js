// Kelas untuk menangani Order
class Order {
  constructor(nama, email, kopi, jumlah) {
    this.nama = nama;
    this.email = email;
    this.kopi = kopi;
    this.jumlah = jumlah;
  }

  // Menampilkan konfirmasi pesanan
  konfirmasi() {
    alert(`Terima kasih ${this.nama}, pesanan Anda untuk ${this.jumlah} ${this.kopi} telah diterima!`);
  }

  // Mencetak detail pesanan ke konsol
  cetak() {
    console.log(`Pesanan dari ${this.nama} (${this.email}): ${this.jumlah} ${this.kopi}`);
  }
}

// Fungsi yang menangani pengiriman form order
function kirimOrder(event) {
  event.preventDefault();

  const form = event.target;
  const formData = new URLSearchParams();

  formData.append("nama", form.nama.value);
  formData.append("email", form.email.value);
  formData.append("kopi", form.kopi.value);
  formData.append("jumlah", form.jumlah.value);

  fetch("/submit", {
    method: "POST",
    body: formData,
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    }
  })
  .then(response => response.text())
  .then(result => {
    alert(result); // atau tampilkan di UI
    form.reset();
  })
  .catch(error => {
    alert("Gagal mengirim pesanan: " + error);
  });
}


// Menghubungkan form dengan event listener
document.addEventListener("DOMContentLoaded", function () {
  const contactForm = document.getElementById('contactForm');
  contactForm.addEventListener('submit', kirimOrder);
  
  // Menambahkan fitur scroll otomatis ke form order
  const orderButtons = document.querySelectorAll(".order-btn");
  const orderForm = document.querySelector("#Order form");
  const kopiSelect = orderForm.querySelector("select[name='kopi']");

  orderButtons.forEach(function (button) {
    button.addEventListener("click", function () {
      const kopi = this.getAttribute("data-kopi");

      // Mengisi dropdown otomatis dengan pilihan kopi
      kopiSelect.value = kopi;

      // Scroll otomatis ke form order
      document.querySelector("#Order").scrollIntoView({
        behavior: "smooth"
      });
    });
  });
});