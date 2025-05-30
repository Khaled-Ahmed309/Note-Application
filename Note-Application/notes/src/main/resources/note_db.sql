PGDMP  ,    6                }           notespplication    17.4    17.4     )           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            *           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            +           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            ,           1262    65536    notespplication    DATABASE     u   CREATE DATABASE notespplication WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en-US';
    DROP DATABASE notespplication;
                     postgres    false            �            1259    73757    notes    TABLE     �   CREATE TABLE public.notes (
    note_id integer NOT NULL,
    title character varying(50),
    content text,
    user_id integer
);
    DROP TABLE public.notes;
       public         heap r       postgres    false            �            1259    73756    notes_note_id_seq    SEQUENCE     �   CREATE SEQUENCE public.notes_note_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.notes_note_id_seq;
       public               postgres    false    220            -           0    0    notes_note_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.notes_note_id_seq OWNED BY public.notes.note_id;
          public               postgres    false    219            �            1259    65555    users    TABLE     �   CREATE TABLE public.users (
    user_id integer NOT NULL,
    name character varying(50) NOT NULL,
    email character varying(50) NOT NULL
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    65554    users_user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.users_user_id_seq;
       public               postgres    false    218            .           0    0    users_user_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;
          public               postgres    false    217            �           2604    73760    notes note_id    DEFAULT     n   ALTER TABLE ONLY public.notes ALTER COLUMN note_id SET DEFAULT nextval('public.notes_note_id_seq'::regclass);
 <   ALTER TABLE public.notes ALTER COLUMN note_id DROP DEFAULT;
       public               postgres    false    219    220    220            �           2604    65558    users user_id    DEFAULT     n   ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);
 <   ALTER TABLE public.users ALTER COLUMN user_id DROP DEFAULT;
       public               postgres    false    217    218    218            &          0    73757    notes 
   TABLE DATA           A   COPY public.notes (note_id, title, content, user_id) FROM stdin;
    public               postgres    false    220   �       $          0    65555    users 
   TABLE DATA           5   COPY public.users (user_id, name, email) FROM stdin;
    public               postgres    false    218   \       /           0    0    notes_note_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.notes_note_id_seq', 42, true);
          public               postgres    false    219            0           0    0    users_user_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.users_user_id_seq', 45, true);
          public               postgres    false    217            �           2606    73764    notes notes_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.notes
    ADD CONSTRAINT notes_pkey PRIMARY KEY (note_id);
 :   ALTER TABLE ONLY public.notes DROP CONSTRAINT notes_pkey;
       public                 postgres    false    220            �           2606    65560    users users_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    218            �           2606    81920    notes notes_user_id_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.notes
    ADD CONSTRAINT notes_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 B   ALTER TABLE ONLY public.notes DROP CONSTRAINT notes_user_id_fkey;
       public               postgres    false    220    218    4750            &   �   x�m�K�0D��)|DTT�-'`��M ����G�ۓd�j4�<��_�I�eNن'\�s7��\y�Gp�d����L��M�N"o�J!_�KF>T+Mtdڤ���aZl�'k��3L:4\�;�*�&%��-U��ho�j�y��?�o>�      $   e   x�3�t��MM�L����9�z���\F8e�qʘ��1�ʤ%�dT�Ș㔱�)c�S�� ��!n)cN�Ĝ�ל���J���t�������=... )�e�     