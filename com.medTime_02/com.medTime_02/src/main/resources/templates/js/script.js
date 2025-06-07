const container = document.getElementById('cardContainer');
const showRegister = document.getElementById('show-register');
const showLogin = document.getElementById('show-login');

showRegister.addEventListener('click', () => {
  container.classList.add('flipped');
});

showLogin.addEventListener('click', () => {
  container.classList.remove('flipped');
});
