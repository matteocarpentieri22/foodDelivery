var card_titles = document.querySelectorAll(".card-title");
var search_input = document.getElementById("search_input");
$(document).ready(function () {
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('#preview-update-image').attr('src', e.target.result).show();
                        $('#remove-update-image').show();
                    }
                    reader.readAsDataURL(input.files[0]);
                }
            }

            function resetPreview(event) {

                event.preventDefault()
                $('#preview-update-image').attr('src', '').hide();
                $('#remove-update-image').hide();
                $('#photo-update').val('');
            }

            $('#photo-update').on('change', function () {
                readURL(this);
            });

            $('#photo-update-dropzone').on('dragover', function (e) {
                e.preventDefault();
                e.stopPropagation();
                $(this).css('background-color', '#e0e0e0');
            }).on('dragleave', function (e) {
                e.preventDefault();
                e.stopPropagation();
                $(this).css('background-color', '');
            }).on('drop', function (e) {
                e.preventDefault();
                e.stopPropagation();
                $(this).css('background-color', '');
                var files = e.originalEvent.dataTransfer.files;
                $('#photo-update').prop('files', files);
                readURL($('#photo-update')[0]);
            });

            $('#remove-update-image').on('click', function (event) {
                resetPreview(event);
            });
 });


function openPopup(n, d, p, b, av, c) {

        var oldname = document.querySelector('#UpdateForm input[name="Oldname"]');
        var name = document.querySelector('#UpdateForm input[name="name"]');
        var description = document.querySelector('#UpdateForm textarea');
        var price = document.querySelector('#UpdateForm input[name="price"]');
        var bar = document.querySelector('#UpdateForm input[name="bar"]');
        var available = document.querySelector('#UpdateForm input[name="available"]');
        var category = document.querySelector('#UpdateForm select[name="category"]');

        oldname.value = n;
        name.value = n;
        description.value = d;
        price.value = p;

        bar.checked=false
        available.checked=false

        if(b==="true"){
            bar.checked=true
        }else{
            bar.checked=false
        }

        if(av==="true"){
            available.checked=true
        }else{
            available.checked=false
        }

        category.value = c;

 }

/*search_input.addEventListener("input", function() {
     let search_text = this.value.trim().toLowerCase();
     for(let i=0; i<card_titles.length; i++){
         card_title_text = card_titles[i].textContent;
         if(card_title_text.toLowerCase().includes(search_text))
             card_titles[i].closest(".card").parentElement.style.display = "block";
         else
             card_titles[i].closest(".card").parentElement.style.display = "none";

     }
 });*/

 search_input.addEventListener("input", function() {
   let search_text = this.value.trim().toLowerCase();
   for (let i = 0; i < card_titles.length; i++) {
     card_title_text = card_titles[i].textContent;
     if (card_title_text.toLowerCase().includes(search_text)) {
       card_titles[i].closest(".card").parentElement.classList.remove("d-none");
     } else {
       card_titles[i].closest(".card").parentElement.classList.add("d-none");
     }
   }
 });

/*const modifyBut = document.getElementById('modifyBut');
const cardContainer = document.querySelectorAll('.card');

modifyBut.addEventListener('click', () => {
  cardContainer.forEach( function(c){
    c.classList.add("disable-card-hover")
  })
});*/

document.addEventListener('DOMContentLoaded', function() {

    // Selection of the alert and we make the element visible
      var alertElement = document.querySelector('.alert-danger');

      alertElement.style.display = 'block';

      // L'alert rimane visibile per 5 secondi
      setTimeout(function() {
        alertElement.style.display = 'none';
      }, 5000);
});

/*window.onload = function() {
  // Selection of the alert and we make the element visible
  var alertElement = document.querySelector('.alert-danger');

  alertElement.style.display = 'block';

  // L'alert rimane visibile per 5 secondi
  setTimeout(function() {
    alertElement.style.display = 'none';
  }, 5000);
};*/



reset_btn.addEventListener("click", function() {
    for(let i=0; i<card_titles.length; i++){
        card_titles[i].closest(".card").parentElement.style.display = "block";
    }
    search_input.value="";
});


  $('#confirm-delete-modal').on('show.bs.modal', function (event) {
     var button = $(event.relatedTarget) // Button that triggered the modal
     var productName = button.data('product-name') // Extract info from data-* attributes
     var form = $(this).find('form')
     form.find('#Oldname').val(productName)
   })


var btn_scroll_top = document.getElementById("btn_scroll-top");
window.onscroll = function () {
    if (window.pageYOffset > 20)
        btn_scroll_top.style.display = "block";
    else
        btn_scroll_top.style.display = "none";
};

btn_scroll_top.addEventListener("click", function() {
     window.scrollTo({
       top: 0,
       behavior: "smooth",
     });
 });

$(document).ready(function() {
  $('input[maxlength]').on('input', function() {
    var maxLength = $(this).attr('maxlength');
    var length = $(this).val().length;
    if (length >= maxLength) {
      $(this).next('.error').remove();
      $(this).after('<span class="error">You have reached the maximum number of characters</span>');
    } else {
      $(this).next('.error').remove();
    }
  });

  $('textarea[maxlength]').on('input', function() {
      var maxLength = $(this).attr('maxlength');
      var length = $(this).val().length;
      if (length >= maxLength) {
        $(this).next('.error').remove();
        $(this).after('<span class="error">You have reached the maximum number of characters</span>');
      } else {
        $(this).next('.error').remove();
      }
    });

});

