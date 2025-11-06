
document.addEventListener("DOMContentLoaded", function() {
  const dateOfBirthInput = document.getElementById("dateOfBirth");
  if (dateOfBirthInput && dateOfBirthInput.value) {
    const [y, m, d] = dateOfBirthInput.value.split("/");
    const yearInput = document.querySelector("input[name='birthYear']");
    const monthInput = document.querySelector("input[name='birthMonth']");
    const dayInput = document.querySelector("input[name='birthDay']");
    if (yearInput) yearInput.value = y;
    if (monthInput) monthInput.value = m;
    if (dayInput) dayInput.value = d;
  }
});

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  if (!form) return;

  form.addEventListener("submit", function () {
    const y = document.getElementById("birthYear")?.value;
    const m = document.getElementById("birthMonth")?.value.padStart(2, "0");
    const d = document.getElementById("birthDay")?.value.padStart(2, "0");
    const target = document.getElementById("dateOfBirth");

    if (y && m && d && target) {
      target.value = `${y}/${m}/${d}`;
    } else if (target) {
      target.value = "";
    }
  });
});
