package danfei.shulaibao.widget.refresh;

/**
 * 描述：
 * Created by Lee
 * on 2016/9/22.
 */
//public class LoadLayout extends LinearLayout {
//    public static final int mErrorState;
//    public static final int mEmptyState;
//    public static final int mTimeOutState;
//    public static final int mNoNetState;
//    public static final int mLocationFailState;
//    public static final int mNoOrderState;
//    public static final int mNoMessageState;
//    public static final int mNoCollectState;
//    public static final int mSearchEmptyState;
//    public static final int mNoLoginState;
//
//    private View mErrorView;
//    private View mEmptyView;
//    private View mTimeOutView;
//    private View mnoNetView;
//    private View mlocation_fail_view;
//    private View mno_order_view;
//    private View no_message_view;
//    private View no_collect_view;
//    private View search_empty_view;
//    private View no_login_view;
//
//    private int error_view;
//    private int empty_view;
//    private int time_out_view;
//    private int no_net_view;
//    private int location_fail_view;
//    private int no_order_view;
//    private int no_message_view;
//    private int no_collect_view;
//    private int search_empty_view;
//    private int no_login_view;
//
//    <attr name="error_view"  format="reference"/>
//    <attr name="empty_view"  format="reference"/>
//    <attr name="time_out_view"  format="reference"/>
//    <attr name="no_net_view"  format="reference"/>
//
//    <attr name="location_fail_view"  format="reference"/>
//    <attr name="no_order_view"  format="reference"/>
//    <attr name="no_message_view"  format="reference"/>
//    <attr name="no_collect_view"  format="reference"/>
//    <attr name="search_empty_view"  format="reference"/>
//    <attr name="no_login_view"  format="reference"/>
//
//    public LoadLayout(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public LoadLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public LoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView(context);
//    }
//
//    private void initView(Context context) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.view_load_layout, null);
//        mContext = context;
//        mIvImg = (ImageView)view.findViewById(R.id.IvImg);
//        mTvTips = (TextView)view.findViewById(R.id.TvTips);
//        mTvReload = (TextView)view.findViewById(R.id.TvReload);
//        mTvReload.setVisibility(View.GONE);
//        addView(view);
//    }
//
//    public void showEmpty(int res){
//        ImageLoadUtil.loadResImage(mContext,R.mipmap.ic_load_fail,mIvImg);
//        mTvTips.setText("怎么搞得，没有数据");
//        mTvReload.setVisibility(View.GONE);
//    }
//
//    public void showFail(int res){
//
//    }
//}
