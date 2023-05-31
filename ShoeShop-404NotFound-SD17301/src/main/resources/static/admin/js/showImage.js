function selectImage() {
  const input = document.getElementById('imageInput');
  const imagePreview = input.dataset.imagePreview;
  input.click();

  input.addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function() {
        const imageUrl = reader.result;
        const image = document.querySelector(imagePreview);
        image.src = imageUrl;
        image.classList.add('selected-image');
      };
      reader.readAsDataURL(file);
    }
  });
}