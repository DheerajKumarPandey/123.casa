package com.a123.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.a123.ChangePasswordActivity;
import com.a123.MapActivity;
import com.a123.NotificationActivity;
import com.a123.PropertyDetailActivity;
import com.a123.R;
import com.a123.SellerHomeActivity;
import com.a123.UpdateProfileActivity;
import com.a123.adapter.NavigationDrawerAdapter;
import com.a123.application.MyApp;
import com.a123.model.User;
import com.a123.utills.AppConstant;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentDrawer extends Fragment {

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;
    private TextView tv_email;
    private TextView tv_name;
    private ImageButton img_btn_edit;

    private TextView nav_item_home;
    private TextView nav_item_map_search;
    private RelativeLayout nav_item_view_listing;
    private RelativeLayout nav_item_fav;
    private RelativeLayout nav_item_share;
    private TextView nav_item_visited;
    private TextView nav_item_notification;
    private TextView nav_item_change_password;
    private TextView nav_item_logout;
    private List<User> users;


    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<String> getData() {
        List<String> data = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            data.add(titles[i]);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(
                R.array.nav_drawer_labels);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer,
                container, false);

        //  recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);


        //   profile_name.setText(MyApp.getApplication().readUser().getName());

        tv_email = (TextView) layout.findViewById(R.id.tv_email);

        tv_name = (TextView) layout.findViewById(R.id.tv_name);

        img_btn_edit = (ImageButton) layout.findViewById(R.id.img_btn_edit);

        nav_item_home = (TextView) layout.findViewById(R.id.nav_item_home);
        nav_item_map_search = (TextView) layout.findViewById(R.id.nav_item_map_search);
        nav_item_view_listing = (RelativeLayout) layout.findViewById(R.id.nav_item_view_listing);
        nav_item_fav = (RelativeLayout) layout.findViewById(R.id.nav_item_fav);
        nav_item_share = (RelativeLayout) layout.findViewById(R.id.nav_item_share);
        nav_item_visited = (TextView) layout.findViewById(R.id.nav_item_visited);
        nav_item_notification = (TextView) layout.findViewById(R.id.nav_item_notification);
        nav_item_change_password = (TextView) layout.findViewById(R.id.nav_item_change_password);
        nav_item_logout = (TextView) layout.findViewById(R.id.nav_item_logout);
        try {
            // tv_name.setText(MyApp.getApplication().readUser().getInfo().get(0).getEmail());
            tv_name.setText(MyApp.getApplication().readUser().get(0).getName());
            tv_email.setText(MyApp.getApplication().readUser().get(0).getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

        nav_item_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        nav_item_map_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });

        nav_item_view_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapActivity.class));
            }
        });

        nav_item_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), PropertyDetailActivity.class));
            }
        });
        nav_item_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nav_item_visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SellerHomeActivity.class));
            }
        });

        nav_item_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));

            }
        });
        nav_item_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });
        nav_item_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.setStatus(AppConstant.IS_LOGIN, false);
                getActivity().finishAffinity();
            }
        });
       /* Picasso.with(getContext()).load(MyApp.getApplication().readUser().getProfilepic()).into(img_profile);
        rl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });*/
//        ll_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                return;
//            }
//        });
//
//        nav_item_scheduled.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 0);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });
//        nav_item_service_request.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 1);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });
//        nav_item_chats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 2);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });
//
//        nav_item_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 3);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });
//        nav_item_wallet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 4);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });
//        nav_item_promo_offer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 5);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });
       /* nav_item_switch_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerListener.onDrawerItemSelected(v, 8);
                mDrawerLayout.closeDrawer(containerView);
            }
        });*/
//        nav_item_invite_friends.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 7);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });
//
//        nav_item_fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerListener.onDrawerItemSelected(v, 6);
//                mDrawerLayout.closeDrawer(containerView);
//            }
//        });

        /*txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*MyApp.setStatus(AppConstant.IS_LOGIN, false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finishAffinity();*//*
            }
        });*/


        img_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
            }
        });

        nav_item_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout,
                      final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                // toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements
            RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(
                                    e.getX(), e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child,
                                        recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null
                    && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        Picasso.with(getContext()).load(MyApp.getApplication().readUser().getProfilepic()).into(img_profile);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}