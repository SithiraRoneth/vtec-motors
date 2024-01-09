/* Created By Sithira Roneth
 * Date :1/2/24
 * Time :23:59
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Income {
    private String desc;
    private double amount;
    private int year;
    private String month;
    private String date;
}
