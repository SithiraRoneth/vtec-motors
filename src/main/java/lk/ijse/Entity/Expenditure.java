/* Created By Sithira Roneth
 * Date :1/3/24
 * Time :00:00
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Expenditure {
    private String desc;
    private double amount;
    private int year;
    private String month;
    private String date;
}
