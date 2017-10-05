package NGPlayerTools;

/**
 * Created by Nils on 01.05.2017.
 */
public enum PatternType {
    Vertical_2,         //two coins
    Vertical_2_pt,      //two coins, with potential on top
    Vertical_3,         //three coins
    Vertical_3_pt,      //two coins, with potential on top
    Horizontal_2,       //two coins
    Horizontal_2_pr,    //two coins, with potential on right side
    Horizontal_2_pl,    //two coins, with potential on left side
    Horizontal_3,       //three coins
    Horizontal_3_pr,    //three coins, with potential on right side
    Horizontal_3_pl,    //three coins, with potential on left side
    Horizontal_3_plr,   //three coins, with potential on left and right side
    Diagonal_2_hr,      //two coins, heading to upper right corner
    Diagonal_2_hl,      //two coins, heading to upper left corner
    //potential can be calculated considering depth of "canyon"/mandatory number of moves to reach diagonal
    Diagonal_3_hr,      //three coins, heading to upper right corner
    Horizontal_2_plr,
    Horizontal_2_plr_d,
    Horizontal_2_pl_d,
    Horizontal_2_pr_d,
    Diagonal_2, Diagonal_3, Diagonal_UNKNOWN, Diagonal_ullr_2, Diagonal_urll_2, Diagonal_urll_3, Diagonal_ullr_3, Vertical, Horizontal, Diagonal, Diagonal_ullr_urll_2, Diagonal_ullr_urll_3, Diagonal_ullr_lrul_2, Diagonal_urll_llur_2, Diagonal_llur_2, Diagonal_lrul_2, Diagonal_lrul_3, Diagonal_llur_3, Diagonal_urll_llur_3, Diagonal_ullr_lrul_3, Diagonal_3_hl       //three coins, heading to upper left corner

    //potential can be calculated considering depth of "canyon"/mandatory number of moves to reach diagonal
}
