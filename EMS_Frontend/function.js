$("#getAllEvents").click(function() {
    $.ajax({
        url: "http://localhost:8080/EMS_Web_exploded/event",
        method: "GET",
        success: function(response) {
            $("#tBody").empty(); // clear old rows
            response.forEach(function(person) {
                let row = `
                    <tr>
                        <td>${person.eid}</td>
                        <td>${person.ename}</td>
                        <td>${person.edescription}</td>
                        <td>${person.edate}</td>
                        <td>${person.eplace}</td>
                    </tr>`;
                $("#tBody").append(row);
            });
        },
        error: function(xhr) {
            console.error("Error fetching events:", xhr);
        }
    });
});
$("#createEvent").click(function(){
    const event = {
        eid: $("#eid").val(),
        ename: $("#ename").val(),
        edescription: $('#edescription').val(),
        edate: $('#edate').val(),
        eplace: $('#eplace').val()

    }
    $.ajax({
        url:"http://localhost:8080/EMS_Web_exploded/event",
        method:"POST",
        contentType:"application/json",
        data:JSON.stringify(event),//converts js object into json
        success:function(response){
            alert('Event created');
            $('#getAllEvents').click();
        },
        error: function () {
            alert("Error creating event");
        }
    });
});
