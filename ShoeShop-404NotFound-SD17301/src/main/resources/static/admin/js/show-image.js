const square = document.getElementById('imageSquare');

    square.addEventListener('click', function() {
      // Create an input element of type file
      const input = document.createElement('input');
      input.type = 'file';

      // Trigger the file selection dialog
      input.click();

      // Handle the selected image
      input.addEventListener('change', function(event) {
        const file = event.target.files[0];

        // Check if a file was selected
        if (file) {
          const reader = new FileReader();

          // Read the contents of the file
          reader.onload = function() {
            const imageUrl = reader.result;

            // Create an image element and set the source
            const image = document.createElement('img');
            image.src = imageUrl;
            image.classList.add('selected-image');

            // Clear the square and append the image
            square.innerHTML = '';
            square.appendChild(image);
          };

          // Read the file as data URL
          reader.readAsDataURL(file);
        }
      });
    });